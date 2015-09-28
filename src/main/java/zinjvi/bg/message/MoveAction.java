package zinjvi.bg.message;

/**
 * Created by Vitaliy on 8/1/2015.
 */
public class MoveAction implements MessageAction {
    private int dx;
    private int dy;

    public MoveAction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }


}
