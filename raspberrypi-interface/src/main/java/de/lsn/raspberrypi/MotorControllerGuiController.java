package de.lsn.raspberrypi;

import java.awt.Component;

import javax.swing.JOptionPane;

public class MotorControllerGuiController {

	private static final String CONTEXT_ROOT = "/raspberrypi-service";
	private static final String CLRF = "\n";
	
	private boolean connected = false;
	
	private MotorControllerGuiView view;

	public MotorControllerGuiController() {
		this.view = new MotorControllerGuiView();
		wire();
	}

	private void wire() {
		this.view.setController(this);
	}
	
	public void connectClicked() {
		if (!connected) {
			System.out.println("connecting ..");
			String uri = view.getUrlTextField().getText();
			System.out.println("Uri: " + uri);
			JerseyMotorConnector.getInstance().start(uri);
			try {
				addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/1/forward/set/"+view.getSelectedEngine1ForwardPinSelection()));
				addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/1/backward/set/"+view.getSelectedEngine1BackwardPinSelection()));
				addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/2/forward/set/"+view.getSelectedEngine2ForwardPinSelection()));
				addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/2/backward/set/"+view.getSelectedEngine2BackwardPinSelection()));
				view.getConnectButton().setText("DISCONNECT");
				connected = true;
				view.getStartButton().setEnabled(true);
			} catch (Exception e) {
				e.printStackTrace();
				showPopup(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
//			TODO IMPLEMENT DISCONNECT
			System.out.println("disconnecting ..");
			System.out.println("not yet ready");
			
			view.getConnectButton().setText("CONNECT");
			connected = false;
			view.getStartButton().setEnabled(false);
			view.getStopButton().setEnabled(false);
		}
	}
	
	public void testClicked() {
		System.out.println("test clicked");
		try {
			try {
				JerseyMotorConnector.getInstance().get(CONTEXT_ROOT+"/greeting");
				showPopup(view, "Test - Successful", "Ok!", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				showPopup(view, "Test - Failed", "Fehler!", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showPopup(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void showPopup(Component view, String content, String title, int messageType) {
		JOptionPane.showMessageDialog(view, content, title, messageType);		
	}

	public void forwardPressed() {
		System.out.println("forward");
		try {
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/1/forward/100"));
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/2/forward/100"));
		} catch (Exception e) {
			e.printStackTrace();
			showPopup(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void backwardPressed() {
		System.out.println("backwards");
		try {
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/1/backward/100"));
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/2/backward/100"));
		} catch (Exception e) {
			e.printStackTrace();
			showPopup(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void turnLeftPressed() {
		System.out.println("turn left");
		try {
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/1/forward/50"));
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/2/forward/100"));
		} catch (Exception e) {
			e.printStackTrace();
			showPopup(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void rotateLeftPressed() {
		System.out.println("rotate left");
		try {
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/1/backward/100"));
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/2/forward/100"));
		} catch (Exception e) {
			e.printStackTrace();
			showPopup(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void turnRightPressed() {
		System.out.println("turn right");
		try {
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/2/forward/100"));
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/1/backward/50"));
		} catch (Exception e) {
			e.printStackTrace();
			showPopup(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void rotateRightPressed() {
		System.out.println("rotate right");
		try {
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/1/forward/100"));
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/2/backward/100"));
		} catch (Exception e) {
			e.printStackTrace();
			showPopup(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void startClicked() {
		System.out.println("start");
		try {
			view.getStopButton().setEnabled(true);
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/1/start"));
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/2/start"));
		} catch (Exception e) {
			e.printStackTrace();
			showPopup(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void stopClicked() {
		System.out.println("stop");
		try {
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/1/stop"));
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/2/stop"));
		} catch (Exception e) {
			e.printStackTrace();
			showPopup(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void haltPressed() {
		System.out.println("halt");
		try {
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/1/halt"));
			addToInfoPanel(JerseyMotorConnector.getInstance().put(CONTEXT_ROOT+"/gpio/motor/2/halt"));
		} catch (Exception e) {
			e.printStackTrace();
			showPopup(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void engine1PowerChanged() {
		System.out.println("Power of engine 1 changed to: ");
	}
	
	public void engine2PowerChanged() {
		System.out.println("Power of engine 2 changed to: ");
	}
	
	private void addToInfoPanel(String s) {
		view.getInfoDataListModel().add(0, s+CLRF);
	}
	
	public void quit() {
		System.out.println("quit");
		view.close();
	}

	public static void main(String[] args) {
		
		MotorControllerGuiController motorController = new MotorControllerGuiController();
		
	}
	
}
