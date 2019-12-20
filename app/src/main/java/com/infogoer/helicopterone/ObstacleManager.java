package com.infogoer.helicopterone;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class ObstacleManager {
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private int score = 0;

    private long startTime;
    private long initTime;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<Obstacle>();
        populateObstacles();
    }

    public boolean playerCollide(RectPlayer player) {
        for(Obstacle ob :obstacles) {
            if (ob.playerCollide(player))
                return true;
        }
        return false;
    }


    private void populateObstacles() {
        int currY = -5*Constants.SCREEN_HEIGHT/4;
        while (currY < 0) {
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update() {
        if (startTime < Constants.INIT_TIME) startTime=Constants.INIT_TIME;
        int elapsedTime = (int)((System.currentTimeMillis() - startTime)/5);
        startTime = System.currentTimeMillis();
        float speed = (float)(Math.sqrt(1+(startTime-initTime)/1000))*Constants.SCREEN_HEIGHT/(10000.0f);
        for(Obstacle ob : obstacles) {
            ob.incrementY(speed + elapsedTime);
        }
        if (obstacles.get(obstacles.size()-1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight,
                    color,
                    xStart,
                    obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap,
                    playerGap));
            obstacles.remove(obstacles.size()-1);
            score++;
        }
    }

    public void draw(Canvas canvas) {
        for(Obstacle ob : obstacles) {
            ob.draw(canvas);
            Paint paint = new Paint();
            paint.setTextSize(75);
            paint.setColor(Color.GREEN);
            canvas.drawText("Score: "+score, 50,100, paint);
        }
    }


}
