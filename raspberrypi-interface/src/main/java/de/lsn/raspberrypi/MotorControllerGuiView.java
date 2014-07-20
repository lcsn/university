package de.lsn.raspberrypi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MotorControllerGuiView extends JDialog {

	private static final long serialVersionUID = 5114910040411544311L;
	
	private static final String TITLE = "Raspberry-Pi MotorController";

	private static final String NULL_ELEMENT = "-";
	
	private MotorControllerGuiController controller;

	private JTextField urlTextField;
	private JPanel urlPanel;
	private JPanel configPanel;
	private JPanel controlPanel;
	private JPanel mainControlPanel;
	private JPanel infoPanel;
	private JButton startButton;
	private JButton stopButton;
	private JButton connectButton;
	private JButton testButton;
	private JButton forwardButton;
	private JButton rotateLeftButton;
	private JButton turnLeftButton;
	private JButton rotateRightButton;
	private JButton turnRightButton;
	private JButton backwardButton;
	private JButton haltButton;
	private JScrollPane infoScrollPane;
	private JList<String> infoDataList;
	private DefaultListModel<String> infoDataListModel;
	private JComboBox<String> engine1ForwardPinSelectionList;
	private JComboBox<String> engine1BackwardPinSelectionList;
	private JComboBox<String> engine2ForwardPinSelectionList;
	private JComboBox<String> engine2BackwardPinSelectionList;

	private JSlider engine1PowerSlider;
	private JSlider engine2PowerSlider;
	private JPanel engine2PowerPanel;
	private JPanel engine1PowerPanel;

	public MotorControllerGuiView() {
		super(new JFrame(TITLE));
		setup();
	}

	private void setup() {
		addComponentListener(new ComponentListener() {
			public void componentShown(ComponentEvent e) {
			}
			public void componentResized(ComponentEvent e) {
				JDialog dialog = (JDialog) e.getSource();
				dialog.setTitle(TITLE + " ("+dialog.getWidth()+"x"+dialog.getHeight()+")");
			}
			public void componentMoved(ComponentEvent e) {
			}
			public void componentHidden(ComponentEvent e) {
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				invokeControllerMethod("quit", "");
			}
		});
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		getContentPane().add(getUrlPanel(), c);
		c.gridx = 0;
		c.gridy = 2;
		getContentPane().add(getConfigPanel(), c);
		c.gridx = 0;
		c.gridy = 3;
		getContentPane().add(getMainControlPanel(), c);
		c.ipady = 100;
		c.gridx = 0;
		c.gridy = 4;
		getContentPane().add(getInfoPanel(), c);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 650);
//		setResizable(false);
		setVisible(true);
	}

	public void setController(MotorControllerGuiController controller) {
		this.controller = controller;
	}
	
	public JPanel getUrlPanel() {
		urlPanel = new JPanel();
		urlPanel.setBorder(BorderFactory.createTitledBorder("Host"));
		urlPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_END;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		urlPanel.add(getUrlTextField(), c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		urlPanel.add(getConnectButton(), c);
		c.gridx = 1;
		c.gridy = 1;
		urlPanel.add(getStartButton(), c);
		c.gridx = 2;
		c.gridy = 1;
		urlPanel.add(getStopButton(), c);
		c.gridx = 3;
		c.gridy = 1;
		urlPanel.add(getTestButton(), c);
		return urlPanel;
	}
	
	public JPanel getInfoPanel() {
		infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createTitledBorder("Info"));
		infoPanel.setLayout(new BorderLayout());
        infoPanel.add(getInfoScrollpane(), BorderLayout.CENTER);
		return infoPanel;
	}

	public JScrollPane getInfoScrollpane() {
		if (null == infoScrollPane) {
//			infoScrollPane = new JScrollPane(getInfoTextArea());
			infoScrollPane = new JScrollPane(getInfoDataList());
		}
		return infoScrollPane;
	}
	
	public JList<String> getInfoDataList() {
		if (null == infoDataList) {
			infoDataList = new JList<String>(getInfoDataListModel());
			infoDataList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			infoDataList.setCellRenderer(new InfoCellRenderer());
			infoDataList.setVisibleRowCount(-1);
		}
		return infoDataList;
	}
	
	public DefaultListModel<String> getInfoDataListModel() {
		if (null == infoDataListModel) {
			infoDataListModel = new DefaultListModel<String>();
		}
		return infoDataListModel;
	}

//	public JTextArea getInfoTextArea() {
//		if (null == infoTextArea) {
//			infoTextArea = new JTextArea();
//			infoTextArea.setLineWrap(true);
//			infoTextArea.setWrapStyleWord(true);
//		}
//		return infoTextArea;
//	}

	public JTextField getUrlTextField() {
		if (null == urlTextField) {
			urlTextField = new JTextField("http://localhost:8080");
		}
		return urlTextField;
	}
	
	public JButton getConnectButton() {
		if (null == connectButton) {
			connectButton = new JButton("CONNECT");
			connectButton.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
				}
				public void mousePressed(MouseEvent e) {
				}
				public void mouseExited(MouseEvent e) {
				}
				public void mouseEntered(MouseEvent e) {
				}
				public void mouseClicked(MouseEvent e) {
					invokeControllerMethod("connect", "Clicked");
				}
			});
		}
		return connectButton;
	}
	
	public JButton getStartButton() {
		if (null == startButton) {
			startButton = new JButton("START");
			startButton.setEnabled(false);
			startButton.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
				}
				public void mousePressed(MouseEvent e) {
				}
				public void mouseExited(MouseEvent e) {
				}
				public void mouseEntered(MouseEvent e) {
				}
				public void mouseClicked(MouseEvent e) {
					invokeControllerMethod("start", "Clicked");
				}
			});
		}
		return startButton;
	}
	
	public JButton getStopButton() {
		if (null == stopButton) {
			stopButton = new JButton("STOP");
			stopButton.setEnabled(false);
			stopButton.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
				}
				public void mousePressed(MouseEvent e) {
				}
				public void mouseExited(MouseEvent e) {
				}
				public void mouseEntered(MouseEvent e) {
				}
				public void mouseClicked(MouseEvent e) {
					invokeControllerMethod("stop", "Clicked");
				}
			});
		}
		return stopButton;
	}
	
	public JButton getTestButton() {
		testButton = new JButton("TEST");
		testButton.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				invokeControllerMethod("test", "Clicked");
			}
		});
		return testButton;
	}
	
	public JPanel getConfigPanel() {
		configPanel = new JPanel();
		configPanel.setBorder(BorderFactory.createTitledBorder("Konfiguration"));
		configPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 30;
		c.gridx = 1;
		c.gridy = 0;
		configPanel.add(getForwardLabel(), c);
		c.ipadx = 20;
		c.gridx = 2;
		c.gridy = 0;
		configPanel.add(getBackwardLabel(), c);
		c.ipadx = 0;
		c.gridx = 0;
		c.gridy = 1;
		configPanel.add(getMotor1Label(), c);
		c.gridx = 1;
		c.gridy = 1;
		configPanel.add(getEngine1ForwardPinSelection(), c);
		c.gridx = 2;
		c.gridy = 1;
		configPanel.add(getEngine1BackwardPinSelection(), c);
		c.gridx = 0;
		c.gridy = 2;
		configPanel.add(getMotor2Label(), c);
		c.gridx = 1;
		c.gridy = 2;
		configPanel.add(getEngine2ForwardPinSelection(), c);
		c.gridx = 2;
		c.gridy = 2;
		configPanel.add(getEngine2BackwardPinSelection(), c);
		return configPanel;
	}

	public JLabel getForwardLabel() {
		JLabel forwardLabel = new JLabel("Forward");
		forwardLabel.setHorizontalAlignment(JLabel.CENTER);
		return forwardLabel;
	}

	public JLabel getBackwardLabel() {
		JLabel backwardLabel = new JLabel("Backward");
		backwardLabel.setHorizontalAlignment(JLabel.CENTER);
		return backwardLabel;
	}

	public JLabel getMotor1Label() {
		return new JLabel("Engine 1");
	}

	public JComboBox<String> getEngine1ForwardPinSelection() {
		if (null == engine1ForwardPinSelectionList) {
			engine1ForwardPinSelectionList = new JComboBox<String>(getRaspiPinArray()) {
				@Override
				public void addItem(String item) {
					List<String> items = new ArrayList<String>();
                    for(int i = 0; i < getItemCount(); i++){
                        items.add((String)getItemAt(i));
                    }

                    if(items.size() == 0){
                        super.addItem(item);
                        return;
                    }else{
                        if(item.compareTo(items.get(0)) <= 0){
                            insertItemAt(item, 0);
                        }else{
                            int index = 0;
                            for(int i = 0; i < getItemCount(); i++){
                                if(item.compareTo(items.get(i)) > 0){
                                    index = i;
                                }
                            }
                            insertItemAt(item, index+1);
                        }
                    }
				}
			};
			engine1ForwardPinSelectionList.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					String item = (String) e.getItem();
					if (e.getStateChange() == ItemEvent.SELECTED) {
						System.out.println("SELECTED:"+item);
						if (!item.equals(NULL_ELEMENT)) {
							getEngine1BackwardPinSelection().removeItem(item);
							getEngine2ForwardPinSelection().removeItem(item);
							getEngine2BackwardPinSelection().removeItem(item);
						}
					}
					else {
						System.out.println("DESELECTED:"+item);
						if (!item.equals(NULL_ELEMENT)) {
							getEngine1BackwardPinSelection().addItem(item);
							getEngine2ForwardPinSelection().addItem(item);
							getEngine2BackwardPinSelection().addItem(item);
						}
					}
				}
			});
		}
		return engine1ForwardPinSelectionList;
	}

	public JComboBox<String> getEngine1BackwardPinSelection() {
		if (null == engine1BackwardPinSelectionList) {
			engine1BackwardPinSelectionList = new JComboBox<String>(getRaspiPinArray()) {
				@Override
				public void addItem(String item) {
					List<String> items = new ArrayList<String>();
                    for(int i = 0; i < getItemCount(); i++){
                        items.add((String)getItemAt(i));
                    }

                    if(items.size() == 0){
                        super.addItem(item);
                        return;
                    }else{
                        if(item.compareTo(items.get(0)) <= 0){
                            insertItemAt(item, 0);
                        }else{
                            int index = 0;
                            for(int i = 0; i < getItemCount(); i++){
                                if(item.compareTo(items.get(i)) > 0){
                                    index = i;
                                }
                            }
                            insertItemAt(item, index+1);
                        }
                    }
				}
			};
			engine1BackwardPinSelectionList.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					String item = (String) e.getItem();
					if (e.getStateChange() == ItemEvent.SELECTED) {
						System.out.println("SELECTED:"+item);
						if (!item.equals(NULL_ELEMENT)) {
							getEngine1ForwardPinSelection().removeItem(item);
							getEngine2ForwardPinSelection().removeItem(item);
							getEngine2BackwardPinSelection().removeItem(item);
						}
					}
					else {
						System.out.println("DESELECTED:"+item);
						if (!item.equals(NULL_ELEMENT)) {
							getEngine1ForwardPinSelection().addItem(item);
							getEngine2ForwardPinSelection().addItem(item);
							getEngine2BackwardPinSelection().addItem(item);
						}
					}
				}
			});
		}
		return engine1BackwardPinSelectionList;
	}

	public JLabel getMotor2Label() {
		return new JLabel("Engine 2");
	}

	public JComboBox<String> getEngine2ForwardPinSelection() {
		if (null == engine2ForwardPinSelectionList) {
			engine2ForwardPinSelectionList = new JComboBox<String>(getRaspiPinArray()) {
				@Override
				public void addItem(String item) {
					List<String> items = new ArrayList<String>();
                    for(int i = 0; i < getItemCount(); i++){
                        items.add((String)getItemAt(i));
                    }

                    if(items.size() == 0){
                        super.addItem(item);
                        return;
                    }else{
                        if(item.compareTo(items.get(0)) <= 0){
                            insertItemAt(item, 0);
                        }else{
                            int index = 0;
                            for(int i = 0; i < getItemCount(); i++){
                                if(item.compareTo(items.get(i)) > 0){
                                    index = i;
                                }
                            }
                            insertItemAt(item, index+1);
                        }
                    }
				}
			};
			engine2ForwardPinSelectionList.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					String item = (String) e.getItem();
					if (e.getStateChange() == ItemEvent.SELECTED) {
						System.out.println("SELECTED:"+item);
						if (!item.equals(NULL_ELEMENT)) {
							getEngine1ForwardPinSelection().removeItem(item);
							getEngine1BackwardPinSelection().removeItem(item);
							getEngine2BackwardPinSelection().removeItem(item);
						}
					}
					else {
						System.out.println("DESELECTED:"+item);
						if (!item.equals(NULL_ELEMENT)) {
							getEngine1ForwardPinSelection().addItem(item);
							getEngine1BackwardPinSelection().addItem(item);
							getEngine2BackwardPinSelection().addItem(item);
						}
					}
				}
			});
		}
		return engine2ForwardPinSelectionList;
	}
	
	public JComboBox<String> getEngine2BackwardPinSelection() {
		if (null == engine2BackwardPinSelectionList) {
			engine2BackwardPinSelectionList = new JComboBox<String>(getRaspiPinArray()) {
				@Override
				public void addItem(String item) {
					List<String> items = new ArrayList<String>();
                    for(int i = 0; i < getItemCount(); i++){
                        items.add((String)getItemAt(i));
                    }

                    if(items.size() == 0){
                        super.addItem(item);
                        return;
                    }else{
                        if(item.compareTo(items.get(0)) <= 0){
                            insertItemAt(item, 0);
                        }else{
                            int index = 0;
                            for(int i = 0; i < getItemCount(); i++){
                                if(item.compareTo(items.get(i)) > 0){
                                    index = i;
                                }
                            }
                            insertItemAt(item, index+1);
                        }
                    }
				}
			};
			engine2BackwardPinSelectionList.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					String item = (String) e.getItem();
					if (e.getStateChange() == ItemEvent.SELECTED) {
						System.out.println("SELECTED:"+item);
						if (!item.equals(NULL_ELEMENT)) {
							getEngine1ForwardPinSelection().removeItem(item);
							getEngine1BackwardPinSelection().removeItem(item);
							getEngine2ForwardPinSelection().removeItem(item);
						}
					}
					else {
						System.out.println("DESELECTED:"+item);
						if (!item.equals(NULL_ELEMENT)) {
							getEngine1ForwardPinSelection().addItem(item);
							getEngine1BackwardPinSelection().addItem(item);
							getEngine2ForwardPinSelection().addItem(item);
						}
					}
				}
			});
		}
		return engine2BackwardPinSelectionList;
	}

	public JPanel getMainControlPanel() {
		if (null == mainControlPanel) {
			mainControlPanel = new JPanel();
			mainControlPanel.setBorder(BorderFactory.createTitledBorder("Controls"));
			mainControlPanel.setLayout(new BorderLayout());
			mainControlPanel.add(getEngine1PowerPanel(), BorderLayout.WEST);
			mainControlPanel.add(getControlPanel(), BorderLayout.CENTER);
			mainControlPanel.add(getEngine2PowerPanel(), BorderLayout.EAST);
		}
		return mainControlPanel;
	}
	
	public JPanel getControlPanel() {
		controlPanel = new JPanel();
//		controlPanel.setBorder(BorderFactory.createTitledBorder("Controls"));
		controlPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		c.ipady = 30;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 0;
		controlPanel.add(getForwardButton(), c);
		c.gridx = 0;
		c.gridy = 0;
		controlPanel.add(getTurnLeftButton(), c);
		c.gridx = 0;
		c.gridy = 1;
		controlPanel.add(getRotateLeftButton(), c);
		c.gridx = 2;
		c.gridy = 0;
		controlPanel.add(getTurnRightButton(), c);
		c.gridx = 2;
		c.gridy = 1;
		controlPanel.add(getRotateRightButton(), c);
		c.ipadx=0;
		c.gridx = 1;
		c.gridy = 2;
		controlPanel.add(getBackwardButton(), c);
		c.gridx = 1;
		c.gridy = 1;
		controlPanel.add(getHaltButton(), c);
		return controlPanel;
	}

	public JPanel getEngine1PowerPanel() {
		if (null == engine1PowerPanel) {
			engine1PowerPanel = new JPanel();
			engine1PowerPanel.setLayout(new BorderLayout());
			engine1PowerPanel.add(getEngine1PowerSlider(), BorderLayout.CENTER);
			engine1PowerPanel.add(new JLabel("Engine 1"), BorderLayout.SOUTH);
		}
		return engine1PowerPanel;
	}
	
	public JSlider getEngine1PowerSlider() {
		if (null == engine1PowerSlider) {
			engine1PowerSlider = new JSlider(JSlider.VERTICAL);
			engine1PowerSlider.setMinimum(0);
			engine1PowerSlider.setMaximum(100);
			engine1PowerSlider.setMajorTickSpacing(10);
			engine1PowerSlider.setMinorTickSpacing(5);
			engine1PowerSlider.createStandardLabels(1);
			engine1PowerSlider.setPaintTicks(true);
			engine1PowerSlider.setPaintLabels(true);
			engine1PowerSlider.setValue(0);
			engine1PowerSlider.setComponentOrientation (ComponentOrientation.RIGHT_TO_LEFT);
			engine1PowerSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					if (!source.getValueIsAdjusting()) {
						invokeControllerMethod("engine1Power", "Changed");
					}
				}
			});
		}
		return engine1PowerSlider;
	}
	
	public JPanel getEngine2PowerPanel() {
		if (null == engine2PowerPanel) {
			engine2PowerPanel = new JPanel();
			engine2PowerPanel.setLayout(new BorderLayout());
			engine2PowerPanel.add(getEngine2PowerSlider(), BorderLayout.CENTER);
			engine2PowerPanel.add(new JLabel("Engine 2"), BorderLayout.SOUTH);
		}
		return engine2PowerPanel;
	}
	
	public JSlider getEngine2PowerSlider() {
		if (null == engine2PowerSlider) {
			engine2PowerSlider = new JSlider(JSlider.VERTICAL);
			engine2PowerSlider.setMinimum(0);
			engine2PowerSlider.setMaximum(100);
			engine2PowerSlider.setMajorTickSpacing(10);
			engine2PowerSlider.setMinorTickSpacing(5);
			engine2PowerSlider.createStandardLabels(1);
			engine2PowerSlider.setPaintTicks(true);
			engine2PowerSlider.setPaintLabels(true);
			engine2PowerSlider.setValue(0);
			engine2PowerSlider.setComponentOrientation (ComponentOrientation.LEFT_TO_RIGHT);
			engine2PowerSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					if (!source.getValueIsAdjusting()) {
						invokeControllerMethod("engine2Power", "Changed");
					}
				}
			});
		}
		return engine2PowerSlider;
	}

	public JButton getForwardButton() {
		forwardButton = new JButton("Forward");
//		forwardButton.addActionListener((a) -> System.out.println("forwards pressed"));
		forwardButton.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				invokeControllerMethod("halt", "Pressed");
			}
			public void mousePressed(MouseEvent e) {
				invokeControllerMethod("forward", "Pressed");
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
			}
		});
		return forwardButton;
	}
	
	public JButton getRotateLeftButton() {
		rotateLeftButton = new JButton("Rotate Left");
//		turnLeftButton.addActionListener((a) -> System.out.println("turn left pressed"));
		rotateLeftButton.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				invokeControllerMethod("halt", "Pressed");
			}
			public void mousePressed(MouseEvent e) {
				invokeControllerMethod("rotateLeft", "Pressed");
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
			}
		});
		return rotateLeftButton;
	}
	
	public JButton getTurnLeftButton() {
		turnLeftButton = new JButton("Left");
//		turnLeftButton.addActionListener((a) -> System.out.println("turn left pressed"));
		turnLeftButton.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				invokeControllerMethod("halt", "Pressed");
			}
			public void mousePressed(MouseEvent e) {
				invokeControllerMethod("turnLeft", "Pressed");
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
			}
		});
		return turnLeftButton;
	}
	
	public JButton getRotateRightButton() {
		rotateRightButton = new JButton("Rotate Right");
//		turnLeftButton.addActionListener((a) -> System.out.println("turn left pressed"));
		rotateRightButton.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				invokeControllerMethod("halt", "Pressed");
			}
			public void mousePressed(MouseEvent e) {
				invokeControllerMethod("rotateRight", "Pressed");
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
			}
		});
		return rotateRightButton;
	}
	
	public JButton getTurnRightButton() {
		turnRightButton = new JButton("Right");
//		turnRightButton.addActionListener((a) -> System.out.println("turn right pressed"));
		turnRightButton.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				invokeControllerMethod("halt", "Pressed");
			}
			public void mousePressed(MouseEvent e) {
				invokeControllerMethod("turnRight", "Pressed");
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
			}
		});
		return turnRightButton;
	}
	
	public JButton getBackwardButton() {
		backwardButton = new JButton("Backward");
//		backwardButton.addActionListener((a) -> System.out.println("backwards pressed"));
		backwardButton.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				invokeControllerMethod("halt", "Pressed");
			}
			public void mousePressed(MouseEvent e) {
				invokeControllerMethod("backward", "Pressed");
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
			}
		});
		return backwardButton;
	}
	
	public JButton getHaltButton() {
		haltButton = new JButton("Halt");
//		stopButton.addActionListener((a) -> System.out.println("stop pressed"));
		haltButton.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				invokeControllerMethod("halt", "Pressed");
			}
		});
		return haltButton;
	}
	
	private void invokeControllerMethod(String componentName, String suffix) {
		try {
			Method method = controller.getClass().getDeclaredMethod(componentName+suffix);
			method.invoke(controller);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void close() {
//		dispose();
		System.exit(-1);
	}	

	private String[] getRaspiPinArray() {
		String pins[] = {NULL_ELEMENT, "0", "1", "2", "3", "4",
			"5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"};
		return pins;
	}
	
//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				MotorControllerGuiView motorView = new MotorControllerGuiView();
//			}
//		});
//	}
	
	private class InfoCellRenderer implements ListCellRenderer<String> {

		@Override
		public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
			JPanel p = new JPanel();
			p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			p.setLayout(new BorderLayout());
			p.add(new JLabel("#"+(list.getModel().getSize()-index)+" > "+value), BorderLayout.CENTER);
			return p;
		}
		
	}

	public String getSelectedEngine1ForwardPinSelection() throws Exception {
		if (engine1ForwardPinSelectionList.getSelectedItem().equals(NULL_ELEMENT)) {
			throw new Exception("Engine 1 forward: Pls choose a pin");
		}
		return (String) engine1ForwardPinSelectionList.getSelectedItem();
	}

	public String getSelectedEngine1BackwardPinSelection() throws Exception {
		if (engine1BackwardPinSelectionList.getSelectedItem().equals(NULL_ELEMENT)) {
			throw new Exception("Engine 1 backward: Pls choose a pin");
		}
		return (String) engine1BackwardPinSelectionList.getSelectedItem();
	}

	public String getSelectedEngine2ForwardPinSelection() throws Exception {
		if (engine2ForwardPinSelectionList.getSelectedItem().equals(NULL_ELEMENT)) {
			throw new Exception("Engine 2 forward: Pls choose a pin");
		}
		return (String) engine2ForwardPinSelectionList.getSelectedItem();
	}

	public String getSelectedEngine2BackwardPinSelection() throws Exception {
		if (engine2BackwardPinSelectionList.getSelectedItem().equals(NULL_ELEMENT)) {
			throw new Exception("Engine 2 backward: Pls choose a pin");
		}
		return (String) engine2BackwardPinSelectionList.getSelectedItem();
	}
	
}

