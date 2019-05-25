import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import com.leapmotion.leap.Gesture.Type;
import java.awt.Event;
import java.awt.KeyEventDispatcher;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
class Mylistener extends Listener {
	public void onInit(Controller controller) {
        System.out.println("Initialized");
    }


    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }
	public Robot robot;
	public void onConnect(Controller controller) {
		System.out.println("Connected");
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
	}
	public void sleep(int t)
	{
		
		try { Thread.sleep(t);
		}
		
		catch(Exception e)
		{
			
		}
				
	}
	public void onFrame(Controller controller) {
		try {
			robot=new Robot();
		}
		catch(Exception e) {
			
		}
		Frame frame=controller.frame();
		InteractionBox box= frame.interactionBox();
		for(Finger f : frame.fingers()) {
			if(f.type()==Finger.Type.TYPE_INDEX) {
				Vector fpos=f.stabilizedTipPosition();
int sensitivity=2;

				Vector bfpos=box.normalizePoint(fpos);
				Dimension screen=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
				robot.mouseMove((int)(sensitivity*screen.width* bfpos.getX()),(int)(screen.height- (bfpos.getY()*screen.height))*sensitivity);
				
			}
		}
		for(Gesture g:frame.gestures())
		{
			if(g.type()==Type.TYPE_CIRCLE)
			{
				CircleGesture circle=new CircleGesture();
	             if(circle.pointable().direction().angleTo(circle.normal())<=Math.PI/4)
	             {
	            	 robot.mouseWheel(1);
	            	 sleep(50);
	             }
	             else {
	            	 
	            	 robot.mouseWheel(-1);
	            	 sleep(50);
	                }
		    }
                   else if(g.type()==Type.TYPE_SCREEN_TAP)
                     {
                       robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                     }
                   else if(g.type()==Type.TYPE_SWIPE && g.state()==State.STATE_START)
                     {
                       robot.keyPress(KeyEvent.VK_WINDOWS);
                        robot.keyRelease(KeyEvent.VK_WINDOWS);
                     }
		}
		
		
	}
}
public  class leapMouse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Controller controller=new Controller();
		Mylistener l=new Mylistener();
		controller.addListener(l);
		try {
			System.in.read();
		}
		catch(Exception e){
			
		}
		}
	}

