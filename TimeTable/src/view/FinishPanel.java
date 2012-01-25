package view;

import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.TimeTableCourse;
import model.TimeTableDay;
import model.TimeTableSession;

import view.TimeTableApp.AppStates;

public class FinishPanel extends TimeTablePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5105592160638886816L;
	JLabel finisMessage = new JLabel("");
	JCheckBox exportOption = new JCheckBox("Export the time table to a file");
	
	List<TimeTableDay> timeTableEntries;
	
	public FinishPanel(TimeTableApp parent, List<TimeTableDay> timeTableEntries) {
		super(parent, AppStates.Finish);
		
		this.timeTableEntries = timeTableEntries;
		
		LayoutManager layout = new BorderLayout(100, 100);
		this.setLayout(layout);
		
		this.add(exportOption, BorderLayout.CENTER);
	}
	
	public boolean canProceed() {
		return false;
	}
	
	public boolean canRevert() {
		return false;
	}
	
	public boolean canFinish() {
		return true;
	}
	
	public boolean canCancel() {
		return false;
	}
	
	public void handleFinish() {
		if(exportOption.isSelected()) {
			JFileChooser timeTableOut = new JFileChooser();
			timeTableOut.addChoosableFileFilter(new FileNameExtensionFilter("Portable Document Format (pdf)", "pdf"));
			int retVal = timeTableOut.showOpenDialog(this.getParent());
			
			String timeTableFile = "";
			if (retVal == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = timeTableOut.getSelectedFile();
	            timeTableFile = selectedFile.getAbsolutePath();
	        }			
			
			if(!timeTableFile.endsWith(".pdf")) {
				timeTableFile += ".pdf";
			}
			
			try {
				Document document = new Document();
				PdfWriter.getInstance(document, new FileOutputStream(timeTableFile));
				document.open();
				
				for(TimeTableDay day : timeTableEntries) {
					boolean headerPrintedForDay = false;
					document.add(new Paragraph("Day " + (day.getDayNumber()+1)));
					List<TimeTableSession> sessions = day.getSessions();
					
					int roomCount = sessions.get(0).getRooms();
					PdfPTable table = new PdfPTable(roomCount);
					boolean colorToggle = true;
					for(TimeTableSession session : sessions) {
						roomCount = session.getRooms();
						if(!headerPrintedForDay) {
							for(int i = 0; i < roomCount; i++) {
								table.addCell(new Phrase("Room " + (i+1), FontFactory.getFont("Times-Bold", 8, BaseColor.BLACK)));
							}
							
							headerPrintedForDay = true;
						} 
						
						for(int i = 0; i < roomCount; i++) {
							TimeTableCourse course = session.getCourse(i);
							if(course == null) {
								table.addCell(new Phrase("-", FontFactory.getFont("Times", 8, (colorToggle?BaseColor.BLUE:BaseColor.MAGENTA))));
							} else {
								table.addCell(new Phrase(course.getCourseName(), FontFactory.getFont("Times", 8, (colorToggle?BaseColor.BLUE:BaseColor.MAGENTA))));
							}
						}
						colorToggle = !colorToggle;
					}

					if(table!=null) {
						document.add(table);
					}
					document.add(new Paragraph(""));
					document.add(new Paragraph(""));
				}
				
				document.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(getParent(), e.getMessage());
			}
			
			catch(Exception e) {
				JOptionPane.showMessageDialog(getParent(), e.getMessage());
				e.printStackTrace();
			}

			
		}
			
		getParent().terminate();
	}
	
}
