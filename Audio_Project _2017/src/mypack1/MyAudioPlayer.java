package mypack1;

import javax.swing.filechooser.FileFilter;
import javax.media.Manager;
import javax.media.Time;
import javax.media.Player;
import javax.media.NoPlayerException;
import javax.media.CannotRealizeException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.awt.*;

/** 
 * @author 
 * Piyush Pant
 */

public class MyAudioPlayer extends JFrame {
	/**
	 * Declaring reference Variables For GUI Components and Other audio Player Components
	 */
	private JPanel cpanel;        
	private JButton openbutton,stopbutton,playbutton; // declaring Buttons for play,browse,stop.
	private JFileChooser jc;   // JFilechooser reference variable declared.
	private JLabel desclbl,stoplbl,playlbl,browselbl; 
	private Time resume;    // resume will get the time at which player have to resume the next time
	
	/*
	 * Declaring boolean variables for checking state of the player ,
	 * Either play or paused state.
	 * Both are initially false denoting play state.
	 */
	boolean ispaused = false;
	boolean isplay = false;
	
	private static String temp=""; //temp will hold the file's absolute pathname
	
	/**
	 * Defining Default Constructor which will initialize all the GUI and other Components.
	 */
	public MyAudioPlayer() {
		super("Audio Player");
		getContentPane().setFont(new Font("SansSerif", Font.BOLD, 12));
		setFont(new Font("Chiller", Font.BOLD, 12));
		
		/**
		 *  Invoked when the user attempts to close the window from the window's system menu. 
		 */
		addWindowListener( new WindowAdapter() {
			 public void windowClosing(WindowEvent we) {
			 System.exit(0);
			 }
			});
		
		/**
		 * Defining and creating GUI using different Swing Components.
		 */
		setBackground(SystemColor.activeCaption);
		getContentPane().setLayout(null);
		cpanel = new JPanel();
		cpanel.setBorder(new LineBorder(new Color(0, 51, 204), 3));
		cpanel.setBackground(new Color(51, 153, 204));
		cpanel.setBounds(10, 51, 293, 78);
		getContentPane().add(cpanel);
		cpanel.setLayout(null);
		
		playbutton = new JButton();
		//Setting icon for Play button.
		playbutton.setIcon(new ImageIcon(MyAudioPlayer.class.getResource("/mypack1/Play.gif")));  
		playbutton.setBounds(128, 11, 31, 31);
		cpanel.add(playbutton);
		
		openbutton = new JButton();
		//Setting icon for Browse button.
		openbutton.setIcon(new ImageIcon(MyAudioPlayer.class.getResource("/mypack1/folder.png")));  
		openbutton.setBounds(27, 11, 41, 31);
		cpanel.add(openbutton);
		
		stopbutton = new JButton("");
		// Setting icon for Stop button.
		stopbutton.setIcon(new ImageIcon(MyAudioPlayer.class.getResource("/mypack1/Stop.gif"))); 
		stopbutton.setBounds(227, 11, 31, 31);
		cpanel.add(stopbutton);
		
		/** Defining Labels... */
	    browselbl = new JLabel("Browse ");
		browselbl.setForeground(Color.BLACK);
		browselbl.setFont(new Font("Calibri", Font.BOLD, 12));
		browselbl.setBounds(30, 53, 41, 14);
		cpanel.add(browselbl);
		
		playlbl= new JLabel("Play/Pause");
		playlbl.setForeground(Color.BLACK);
		playlbl.setFont(new Font("Calibri", Font.BOLD, 12));
		playlbl.setBounds(116, 53, 55, 14);
		cpanel.add(playlbl);
		
		stoplbl = new JLabel("Stop");
		stoplbl.setForeground(Color.BLACK);
		stoplbl.setFont(new Font("Calibri", Font.BOLD, 12));
		stoplbl.setBounds(230, 52, 31, 14);
		cpanel.add(stoplbl);
		
		desclbl = new JLabel("Welcome , Please select a (.wav) file.");
		desclbl.setFont(new Font("SansSerif", Font.BOLD, 12));
		desclbl.setBounds(10, 11, 293, 19);
		getContentPane().add(desclbl);
		
		registerAction();
	}
	
	/**
	 * Defining method registerAction() which
	 * will register all the events to Buttons created,
	 * play event, pause event, stop event and browse event.
	 */
	private void registerAction()
	{
		playbutton.addActionListener(new Handler());
		stopbutton.addActionListener(new Handler());
		openbutton.addActionListener(new Handler());
	}
	
	/**
	 * Declaring and initializing reference variable 
	 * of Player Interface.
	 * Player interface for rendering and 
	 * controlling time based media data.
	 */
	private Player aplayer = null;
	
	/** Defining Method urlHandler that will get 
	 * path of media as url and will create a Realized player.
	 * @param url
	 * @throws IOException
	 * @throws NoPlayerException
	 * @throws CannotRealizeException
	 */
	public void urlHandler(URL url) throws IOException,NoPlayerException,CannotRealizeException
	{
		   /** Manager class is access point for obtaining system dependent resources*/
			aplayer = Manager.createRealizedPlayer(url); // Creating Realized Player
	}
	
	/**
	 * Defining method urlCon() 
	 * @param file
	 * @throws IOException
	 * @throws NoPlayerException
	 * @throws CannotRealizeException
	 */
	
	public void urlCon(File file) throws IOException,NoPlayerException,CannotRealizeException
										,MalformedURLException
	{
			urlHandler(file.toURI().toURL()); // Convert a file abstract pathname into URL 
	}
	
	/**
	 * loadFile() method
	 * will get file from a system location with abstract pathname
	 * @return File object file
	 */
	public File loadFile()
	{
		File file = new File(temp);
		return file;
	}
	
	/**
	 * openDir() method
	 * will implement a simple mechanism for the user to choose a file,
	 * using JFileChooser Class of Java Swing.
	 */
	public void openDir()
	{
		jc = new JFileChooser();  // jc reference variable instantiated
		
		/** FileFilter reference variable instantiated,
		 *  will filter the set of files shown to the user,
		 *  and can be set on a JFileChooser to keep
		 *  unwanted files from appearing in the directory listing.
		 */
		FileFilter wavfilter = new FileFilter() {
			@Override
			public String getDescription()
			{
			   /*return string to display file format to user
				in directory dialog window.(Here it is *.wav file)*/
				return "Sound file (*.wav)"; 
			}
			@Override
			public boolean accept(File fout)
			{
				if(fout.isDirectory())
			   //checks whether the file denoted by this abstract pathname is a directory.
					return true; 
				else
					/*analyze and show files in filechooser dialog window
					  which ends with *.wav extension  */
					return fout.getName().toLowerCase().endsWith(".wav");
			}
		};
		jc.setFileFilter(wavfilter);
		jc.setDialogTitle("Open Audio File");
		jc.setAcceptAllFileFilterUsed(false);
		int rval = jc.showOpenDialog(MyAudioPlayer.this); 
		if(rval==JFileChooser.APPROVE_OPTION)
		//APPROVE_OPTION returns value if approve (ok,yes) is chosen.
		{
			try {
				temp = jc.getSelectedFile().getAbsolutePath();
			 //set description label desclbl as file's absolute path.
				desclbl.setText("File Selected "+" ("+temp+")");
			}
			catch(NullPointerException ne)
			{
				desclbl.setText("Cannot get Path");
			}
		}
		if(rval==JFileChooser.CANCEL_OPTION) 
		  //CANCEL_OPTION return value if cancel is chosen.
			desclbl.setText("You Pressed Cancel");
	}
		
	/**
	 * Method play()
	 * implementing play and pause functionality of a player
	 */
	public void play()
	{
		try{
			if(isplay==false&&ispaused==false) //if true ,media start playing
			{
					aplayer.start();
					//changes icon from play icon to pause icon
					playbutton.setIcon(new
				    ImageIcon(MyAudioPlayer.class.getResource("/mypack1/Pause.png")));
					desclbl.setText("Playing: "+jc.getSelectedFile().getName());
					isplay=true;
					ispaused=true;
					playlbl.setText("Pause"); // changes label to "Pause".
			}
			else // else block define pause functionality
			{
					resume = aplayer.getMediaTime();
					aplayer.stop();
					playbutton.setIcon(new ImageIcon(MyAudioPlayer.class.getResource("/mypack1/Play.gif")));
					desclbl.setText("Paused");
					ispaused = false;
					isplay=false;
					playlbl.setText("Play"); //changes label to "Play" .
			}
			playlbl.setBounds(131, 53, 31, 14);
		 }
		catch(Exception e)
		{
	     /*if user try to press play button
	      * then to handle the thrown exception
	      * description will be shown as label.
	      */
		  desclbl.setText("No File Selected"); 
		}
	}
	
	/**
	 * Method stop()
	 * to implement stop functionality of a player.
	 * Will stop the media and reset the media to start time
	 * i.e 0.0 
	 */
	public void stop()
	{
		try {
		    aplayer.stop();
			desclbl.setText("Audio stopped..");
			// on stopping pause icon turns to play.
			playbutton.setIcon(new ImageIcon(MyAudioPlayer.class.getResource("/mypack1/Play.gif")));
			isplay = false;
			ispaused = false;
			aplayer.setMediaTime(new Time(0.0)); // reset media time to start time.
		    }
		catch(NullPointerException e)
		{
			desclbl.setText("No song Selected");
		}
	}
	
	/**
	 * Handler class implements ActionListener
	 * made to Handle the fired events.
	 * The source of event is compared with the
	 * incoming event and hence only handles
	 * events fired from buttons.
	 */
	class Handler implements ActionListener {
		public void actionPerformed(ActionEvent e)
		{
		   //getSource() gives a reference to the object that the event came from.
			Object source = e.getSource(); 
			if(source instanceof JButton)
			{
				// source typecasted to button reference
				JButton button = (JButton) source;  
				if(button == openbutton) // if reference of browse button
					openDir();
				else if(button == playbutton) // if reference of play button
					 play();
				else stop();  // if reference of stop button
			}
		}
	}

	/**
	 * Launch the application.
	 * main() method
	 */
	public static void main(String[] args) {
				try {
					MyAudioPlayer frame = new MyAudioPlayer();
					frame.setSize(330,175);
					frame.setVisible(true);
					File audiofile=null;
					while(temp.length()<2)
					{
					Thread.sleep(100); // waits for the file to get selected.
					}
					audiofile= frame.loadFile();// file reference returned from loadFile()
					frame.urlCon(audiofile); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
}

