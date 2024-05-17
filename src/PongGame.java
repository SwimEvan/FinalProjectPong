import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongGame extends JFrame {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int PADDLE_WIDTH = 20;
    private final int PADDLE_HEIGHT = 100;
    private final int BALL_SIZE = 20;
    private final int PADDLE_SPEED = 5;
    private int paddle1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int ballX = WIDTH / 2 - BALL_SIZE / 2;
    private int ballY = HEIGHT / 2 - BALL_SIZE / 2;
    private int ballXSpeed = 2;
    private int ballYSpeed = 2;

    public PongGame() {
        setTitle("Pong Game");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, WIDTH, HEIGHT);

                g.setColor(Color.WHITE);
                g.fillRect(0, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
                g.fillRect(WIDTH - PADDLE_WIDTH, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);

                g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
            }
        };

        panel.setFocusable(true);
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W && paddle1Y > 0) {
                    paddle1Y -= PADDLE_SPEED;
                } else if (key == KeyEvent.VK_S && paddle1Y < HEIGHT - PADDLE_HEIGHT) {
                    paddle1Y += PADDLE_SPEED;
                } else if (key == KeyEvent.VK_UP && paddle2Y > 0) {
                    paddle2Y -= PADDLE_SPEED;
                } else if (key == KeyEvent.VK_DOWN && paddle2Y < HEIGHT - PADDLE_HEIGHT) {
                    paddle2Y += PADDLE_SPEED;
                }
                panel.repaint();
            }
        });

        add(panel);
        setVisible(true);

        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ballX += ballXSpeed;
                ballY += ballYSpeed;

                if (ballY <= 0 || ballY >= HEIGHT - BALL_SIZE) {
                    ballYSpeed = -ballYSpeed;
                }

                if (ballX <= PADDLE_WIDTH && ballY >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
                    ballXSpeed = -ballXSpeed;
                }

                if (ballX >= WIDTH - PADDLE_WIDTH - BALL_SIZE && ballY >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
                    ballXSpeed = -ballXSpeed;
                }

                if (ballX <= 0 || ballX >= WIDTH - BALL_SIZE) {
                    ballX = WIDTH / 2 - BALL_SIZE / 2;
                    ballY = HEIGHT / 2 - BALL_SIZE / 2;
                    ballXSpeed = -ballXSpeed;
                    ballYSpeed = -ballYSpeed;
                }

                panel.repaint();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        new PongGame();
    }
}
