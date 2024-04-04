import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * DO NOT TOUCH
 * 
 * This class provides a simple interface for displaying the solution to a
 * Hitori puzzle.
 * 
 * Note - You will need to use the Run Configurations to specify the file to solve
 * 
 * @author cmb79981
 *
 */
public class HitoriGUI {

	private JFrame frame;
	private Hitori game;
	private JLabel message;
	private JLabel[][] boxes = new JLabel[0][0];
	private JButton submit;
	private int currRow;
	private static final int BOXES_AREA = 400;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					HitoriGUI window = new HitoriGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HitoriGUI() {
		this.game = null;
		initialize();
	}

	/**
	 * Initialize the unsolved game.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(250 + BOXES_AREA, 250 + BOXES_AREA);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.game = new Hitori(chooser.getSelectedFile().getPath());
		}
		else {
			System.exit(returnVal);
		}

		message = new JLabel("Unsolved Puzzle", SwingConstants.CENTER);
		message.setFont(new Font("SansSerif", Font.PLAIN, 50));
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setBounds(0, 0, 650, 100);
		frame.getContentPane().add(message);

		submit = new JButton("Solve");
		submit.setBounds((250+BOXES_AREA)/2-125, 120 + BOXES_AREA, 250, 50);
		frame.getContentPane().add(submit);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (game.solve()) {
					HitoriGUI.this.message.setText("Solution");
					HitoriGUI.this.colorBoxes();
				} else {
					HitoriGUI.this.message.setText("No Solution Exists");
				}
				HitoriGUI.this.frame.getContentPane().remove(submit);
				HitoriGUI.this.frame.getContentPane().revalidate();
				HitoriGUI.this.frame.getContentPane().repaint();

			}
		});

		

		this.createBoxes();
	}

	/**
	 * Creates the unsolved puzzle with the original numbers all in grey
	 */
	public void createBoxes() {
		int len = this.game.getSize();
		int boxWidth = (BOXES_AREA) / len;
		int startX = (650 - (boxWidth * len)) / 2;
		int y = 100;
		int[][] vals = game.getOriginalGrid();

		this.boxes = new JLabel[len][len];
		Border blackline = BorderFactory.createLineBorder(Color.black);

		for (int row = 0; row < len; row++) {
			int x = startX;
			for (int col = 0; col < len; col++) {
				JLabel temp = new JLabel(vals[row][col] + "", SwingConstants.CENTER);
				temp.setBounds(x, y, boxWidth, boxWidth);
				temp.setBackground(Color.LIGHT_GRAY);
				temp.setOpaque(true);
				temp.setFont(new Font("SansSerif", Font.PLAIN, boxWidth - 5));
				temp.setBorder(blackline);
				frame.add(temp);
				x += boxWidth;
				this.boxes[row][col] = temp;

			}
			y += boxWidth;
		}
	}


	/**
	 * Colors the boxes based on the solution found
	 */
	public void colorBoxes() {
		String s = this.game.toString();
		String[] rows = s.split("\n");
		for (int row = 0; row < this.boxes.length; row++) {
			for (int col = 0; col < this.boxes[row].length; col++) {
				JLabel currBox = this.boxes[row][col];
				String text = currBox.getText();
				if (!rows[row].substring(col * 3, (col + 1) * 3).equals("   ")) {
					currBox.setBackground(Color.WHITE);
					currBox.getAccessibleContext().setAccessibleDescription(text + " is white");
				} else {
					currBox.setBackground(Color.BLACK);
					currBox.setForeground(Color.LIGHT_GRAY);
					currBox.getAccessibleContext().setAccessibleDescription(text + " is black");
				}
			}
		}
	}

}