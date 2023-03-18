import java.util.Random;
class Ball {
    private static double ballSpeed = 6;
    static double Width = 30;
    static double Height = 30;

    // private Color color;
    private double radius = Width/2;

    private double x;
    private double y;

    private double yVelocity;
    private double xVelocity;

    // constructor
    public Ball() {
        resetBall();
        yVelocity = ballSpeed;
        xVelocity = ballSpeed;
    }

    private void move() {
        detectWallCollision();
        x += xVelocity;
        y += yVelocity;
    }
    public void update(){
        move();
    }
    public void reverseX() {
        xVelocity *= -1;
    }
    public void reverseY() {
        yVelocity *= -1;
    }
    public void resetBall() {
        x = GameBoard.Width/2;
        y = GameBoard.Height/2;
        xVelocity *= (new Random()).nextBoolean() ? -1 : 1;
    }

    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public double getRadius() {
        return radius;
    }
    public double getXDirection() {
        return xVelocity;
    }
    public double getYDirection() {
        return xVelocity;
    }
    public void setXVelocity(double angleRad, double direction) {
        // xVelocity = direction * ballSpeed * Math.cos(angleRad);
        xVelocity = direction * ballSpeed;
    }
    public void setYVelocity(double angleRad, double direction) {
        // yVelocity = ballSpeed * Math.sin(angleRad);
        yVelocity = ballSpeed * Math.sin(angleRad);
    }
    private void detectWallCollision() {
        if(y <= 0 )
            reverseY();
        if(y + Height >= GameBoard.Height)
            reverseY();

        // 공 안나가기 위해 잠시 설정해둔 것 실제 게임에선 지워야함
        // if(x < 0 ) 
        //     reverseX();
        // if(x + Width >= GameBoard.Width)
        //     reverseX();
    }
}