package com.base.engine;

import java.util.Random;

public class Monster
{

    public static final float SCALE = 0.7f;
    public static final float SIZEY = SCALE;
    public static final float SIZEX = (float) ((double) SIZEY / (1.9310344827586206896551724137931 * 2.0));
    public static final float START = 0;

    public static final float OFFSET_X = 0.1f;
    public static final float OFFSET_Y = 0.0f;
    public static final float OFFSET_FROM_GROUND = 0.0f;

    public static final float TEX_MIN_X = -OFFSET_X;
    public static final float TEX_MAX_X = -1 - OFFSET_X;
    public static final float TEX_MIN_Y = -OFFSET_Y;
    public static final float TEX_MAX_Y = 1 - OFFSET_Y;

    public static final int STATE_IDLE = 0;
    public static final int STATE_CHASE = 1;
    public static final int STATE_ATTACK = 2;
    public static final int STATE_DYING = 3;
    public static final int STATE_DEAD = 4;

    public static final float MOVE_SPEED = 1.0f;
    public static final float MOVEMENT_STOP_DISTANCE = 1.5f;
    public static final float MONSTER_WIDTH = 0.2f;
    public static final float MONSTER_LENGTH = 0.2f;

    public static final float SHOOT_DISTANCE = 1000.0f;
    public static final float SHOT_ANGLE = 10.0f;
    public static final float ATTACK_CHANCE = 0.5f;
    public static final int MAX_HEALTH = 100;
    public static final int DAMAGE_MIN = 5;
    public static final int DAMAGE_MAX = 30;

    private static Mesh mesh;
    private Material material;
    private Transform transform;
    private Random rand;
    private int state;
    private int health;
    private boolean canLook;
    private boolean canAttack;

    public Monster(Transform transform)
    {
        this.transform = transform;
        this.state = STATE_IDLE;
        this.canAttack = false;
        this.canLook = false;
        this.health = MAX_HEALTH;
        this.rand = new Random();
        material = new Material(new Texture("SSWVA1.png"));

        if (mesh == null)
        {
            Vertex[] vertices = new Vertex[]
            {
                new Vertex(new Vector3f(-SIZEX, START, START), new Vector2f(TEX_MAX_X, TEX_MAX_Y)),
                new Vertex(new Vector3f(-SIZEX, SIZEY, START), new Vector2f(TEX_MAX_X, TEX_MIN_Y)),
                new Vertex(new Vector3f(SIZEX, SIZEY, START), new Vector2f(TEX_MIN_X, TEX_MIN_Y)),
                new Vertex(new Vector3f(SIZEX, START, START), new Vector2f(TEX_MIN_X, TEX_MAX_Y))
            };

            int[] indices = new int[]
            {
                0, 1, 2,
                0, 2, 3
            };

            mesh = new Mesh(vertices, indices);
        }
    }

    public Transform getTransform()
    {
        return transform;
    }

    public Vector2f getSize()
    {
        return new Vector2f(MONSTER_WIDTH, MONSTER_LENGTH);
    }

    public void damage(int amt)
    {
        if (state == STATE_IDLE)
        {
            state = STATE_CHASE;
        }

        health -= amt;
        
        System.out.println("Monster hit");

        if (health <= 0)
        {
            state = STATE_DYING;
        }
    }

    private void idleUpdate(Vector3f orientation, float distance)
    {
        double time = ((double) Time.getTime()) / ((double) Time.SECOND);
        double timeDecimals = time - (double) ((int) time);

        if (timeDecimals < 0.5)
        {
            canLook = true;
        }
        else if (canLook)
        {
            Vector2f lineStart = new Vector2f(transform.getTranslation().getX(), transform.getTranslation().getZ());
            Vector2f castDirection = new Vector2f(orientation.getX(), orientation.getZ());
            Vector2f lineEnd = lineStart.add(castDirection.mul(SHOOT_DISTANCE));

            Vector2f collisionVector = Game.getLevel().checkIntersections(lineStart, lineEnd, false);

            Vector2f playerIntersectVector = Game.getLevel().lineIntersectRect(lineStart, lineEnd,
                    new Vector2f(Transform.getCamera().getPos().getX(), Transform.getCamera().getPos().getZ()),
                    new Vector2f(Player.PLAYER_SIZE, Player.PLAYER_SIZE));

            if (playerIntersectVector != null && (collisionVector == null
                    || playerIntersectVector.sub(lineStart).length() < collisionVector.sub(lineStart).length()))
            {
                System.out.println("We've seen the player!");
                state = STATE_CHASE;
            }

            canLook = false;
        }
    }

    private void chaseUpdate(Vector3f orientation, float distance)
    {
        if (rand.nextDouble() < ATTACK_CHANCE * Time.getDelta())
        {
            state = STATE_ATTACK;
        }

        if (distance > MOVEMENT_STOP_DISTANCE)
        {
            float moveAmount = MOVE_SPEED * (float) Time.getDelta();

            Vector3f oldPos = transform.getTranslation();
            Vector3f newPos = transform.getTranslation().add(orientation.mul(moveAmount));

            Vector3f collisionVector = Game.getLevel().checkCollision(oldPos, newPos, MONSTER_WIDTH, MONSTER_LENGTH);

            Vector3f movementVector = collisionVector.mul(orientation);

            if (movementVector.length() > 0)
            {
                transform.setTranslation(transform.getTranslation().add(movementVector.mul(moveAmount)));
            }

            if (movementVector.sub(orientation).length() != 0)
            {
                Game.getLevel().openDoors(transform.getTranslation());
            }
        }
        else
        {
            state = STATE_ATTACK;
        }
    }

    private void attackUpdate(Vector3f orientation, float distance)
    {
        double time = ((double) Time.getTime()) / ((double) Time.SECOND);
        double timeDecimals = time - (double) ((int) time);

        if (timeDecimals < 0.5)
        {
            canAttack = true;
        }
        else if (canAttack)
        {
            Vector2f lineStart = new Vector2f(transform.getTranslation().getX(), transform.getTranslation().getZ());
            Vector2f castDirection = new Vector2f(orientation.getX(), orientation.getZ()).rotate((rand.nextFloat() - 0.5f) * SHOT_ANGLE);
            Vector2f lineEnd = lineStart.add(castDirection.mul(SHOOT_DISTANCE));

            Vector2f collisionVector = Game.getLevel().checkIntersections(lineStart, lineEnd, false);

            Vector2f playerIntersectVector = Game.getLevel().lineIntersectRect(lineStart, lineEnd,
                    new Vector2f(Transform.getCamera().getPos().getX(), Transform.getCamera().getPos().getZ()),
                    new Vector2f(Player.PLAYER_SIZE, Player.PLAYER_SIZE));

            if (playerIntersectVector != null && (collisionVector == null
                    || playerIntersectVector.sub(lineStart).length() < collisionVector.sub(lineStart).length()))
            {
                Game.getLevel().damagePlayer(rand.nextInt(DAMAGE_MAX - DAMAGE_MIN) + DAMAGE_MIN);
            }
            canAttack = false;
            state = STATE_CHASE;
        }
    }

    private void dyingUpdate(Vector3f orientation, float distance)
    {
        System.out.println("Monster Dead!");
        state = STATE_DEAD;
    }

    private void deadUpdate(Vector3f orientation, float distance)
    {
        
    }

    private void alignWithGround()
    {
        transform.getTranslation().setY(OFFSET_FROM_GROUND);
    }

    private void faceCamera(Vector3f directionToCamera)
    {
        float angleToFaceTheCamera = (float) Math.toDegrees(Math.atan(directionToCamera.getZ() / directionToCamera.getX()));

        if (directionToCamera.getX() < 0)
        {
            angleToFaceTheCamera += 180;
        }

        transform.getRotation().setY(angleToFaceTheCamera + 90);
    }

    public void update()
    {
        Vector3f directionToCamera = Transform.getCamera().getPos().sub(transform.getTranslation());

        float distance = directionToCamera.length();
        Vector3f orientation = directionToCamera.div(distance);

        alignWithGround();
        faceCamera(orientation);

        switch (state)
        {
            case STATE_IDLE:
                idleUpdate(orientation, distance);
                break;
            case STATE_CHASE:
                chaseUpdate(orientation, distance);
                break;
            case STATE_ATTACK:
                attackUpdate(orientation, distance);
                break;
            case STATE_DYING:
                dyingUpdate(orientation, distance);
                break;
            case STATE_DEAD:
                deadUpdate(orientation, distance);
                break;
        }
    }

    public void render()
    {
        Shader shader = Game.getLevel().getShader();
        shader.updateUniforms(transform.getTransformation(), transform.getProjectedTransformation(), material);
        mesh.draw();
    }
}
