import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class MainSnakeGamePanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE=20;
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    //starting body size of the snake
    int snake_bodySize = 4;
    int foodEaten;
    int foodX;
    int foodY;
    char snake_initial_direction='D';
    boolean running = false;
    Timer timer;
    Random random;


    MainSnakeGamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startSnakeGame();
    }
    public void startSnakeGame(){
        newFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        if(running) {
            // FOOD

            g.setColor(Color.yellow);
            g.fillRect(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

            // SNAKE

            for (int i = 0; i < snake_bodySize; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(154, 205, 50));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.green);
            g.setFont(new Font("Arial",Font.BOLD,30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("score: "+foodEaten, (SCREEN_WIDTH-metrics.stringWidth("score: "+foodEaten))/2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    public void move(){
        for(int i=snake_bodySize; i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (snake_initial_direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;

        }
    }
    public void newFood(){
        // takes care of food positioning
        foodX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        foodY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    public void checkFood(){
        if((x[0] == foodX) && (y[0] == foodY)){
            snake_bodySize++;
            foodEaten++;
            newFood();
        }

    }
    public void checkCollision(){
        // checks head and body collision
        for(int i=snake_bodySize; i>0; i--){
            if( (x[0] == x[i] && y[0] == y[i]) ){
                running = false;
            }
        }
        // checks for left border collision
        if(x[0] < 0){
            running = false;
        }
        // checks for right border collision
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        // checks for top border collision
        if(y[0] < 0){
            running = false;
        }
        // checks for bottom border collision
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }

        if(!running){
            timer.stop();
        }


    }
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Arial",Font.BOLD,80));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH-metrics.stringWidth("GAME OVER"))/2,SCREEN_HEIGHT/2);

        g.setColor(Color.green);
        g.setFont(new Font("Arial",Font.BOLD,30));
        FontMetrics metrics0 = getFontMetrics(g.getFont());
        g.drawString("score: "+foodEaten, (SCREEN_WIDTH-metrics0.stringWidth("score: "+foodEaten))/2, g.getFont().getSize());


    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(running){
            move();
            checkFood();
            checkCollision();
        }
        repaint();

    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(snake_initial_direction != 'R'){
                        snake_initial_direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(snake_initial_direction != 'L'){
                        snake_initial_direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(snake_initial_direction != 'D'){
                        snake_initial_direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(snake_initial_direction != 'U'){
                        snake_initial_direction = 'D';
                    }
                    break;
            }

        }
    }
}
