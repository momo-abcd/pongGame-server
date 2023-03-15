public class Paddle {
    // 패들 기본 설정 변수 선언
     final static double Width = 25;
     final static double Height = 100;

    // 이동 관련 변수 선언
    private double y;
    private double x;
    private double yVelocity = 0;

    // contructor
    public Paddle(double initX, double initY) {
        y = initY;
        x = initX;
    }
    
    // 볼이랑 부딪히는지 확인하는 메서드
    public boolean detectBallCollision(Ball ball) {
        double radius = ball.getRadius();
            
        double top = y;
        double bottom = y + Height;
        double left = x;
        double right = x + Width; 

        double bTop = ball.getY() - radius;
        double bBottom = ball.getY() + radius;
        double bLeft = ball.getX() - radius+15;
        double bRight = ball.getX() + radius+15;

        return left < bRight && top < bBottom && right > bLeft
            && bottom > bTop;
    }
    public synchronized void update(double v) {
        setYDirection(v);
        move();
    }
    public synchronized double getX() {
        return x;
    }
    public synchronized double getY() {
        return y;
    }
    // y변위 설정 메서드
    private void setYDirection(double yDirection){
        yVelocity = yDirection;
    }

    // Paddle의 y값 변화 메서드
    private void move() {
        if(detectWallCollision()) return;
        else y += yVelocity;
    }

    // Paddle, 벽 충돌 감지 메소드
    private boolean detectWallCollision() {
        if(yVelocity < 0) {
            if(y<GameBoard.speed) return true;
            return false;
        }else if(yVelocity > 0){
            if(y >= GameBoard.Height - Height) return true;
            return false;
        }else return false;
    }
}