package edu.wm.cs.cs301.AlexPopa.gui;

import android.graphics.Typeface;

import java.util.logging.Logger;

import edu.wm.cs.cs301.AlexPopa.gui.ColorTheme.MazeColors;

/**
 * Implements the screens that are displayed whenever the game is not in 
 * the playing state. The screens shown are the title screen, 
 * the generating screen with the progress bar during maze generation,
 * and the final screen when the game finishes.
 * The only one that is not simple and not covered by this class
 * is the one that shows the first person view of the maze game
 * and the map of the maze when the user really navigates inside the maze.
 * 
 * {@code W&M} specific color settings
 * Green: #115740
 * Gold: #916f41
 * Black: #222222
 * 
 * Design: 
 * white background with green and gold frame 
 * title text large and gold, 
 * normal text small and green
 * small text small and black
 * 
 * @author Peter Kemper
 *
 */
public class SimpleScreens {
	/**
	 * The logger is used to track execution and report issues.
	 */
	private static final Logger LOGGER = Logger.getLogger(SimpleScreens.class.getName());
	private final String errorMsg = "Can't get graphics object to draw on, mitigate this by skipping draw operation";
	    
    /**
     * Draws the title screen, screen content is hard coded
     * @param panel holds the graphics for the off-screen image
     * @param filename is a string put on display for the file
     * that contains the maze, can be null
     */
    public void redrawTitle(MazePanel panel, String filename) {
    	if (null == panel) {
        	LOGGER.warning(errorMsg) ;
        }
        else {
            redrawTitle(panel,filename);
        }
    }
    /**
     * Helper method for redraw to draw the title screen, screen is hard coded
     * @param  gc graphics is the off-screen image, can not be null
     * @param filename is a string put on display for the file
     * that contains the maze, can be null
     */
    private void redrawTitle(MazePanel gc, String filename) {
        // produce white background
    	gc.addBackground(0);
        // write the title 
        updateFontAndColor(gc, largeBannerFont, MazeColors.TITLE_LARGE);
        centerString(gc, "MAZE", 100);
        // write the reference to Paul Falstad
        updateFontAndColor(gc, smallBannerFont, MazeColors.TITLE_SMALL);
        centerString(gc, "by Paul Falstad", 160);
        centerString(gc, "www.falstad.com", 190);
        // write the instructions in black, same smallBannerFont as before
        gc.setColor(ColorTheme.getColor(MazeColors.TITLE_DEFAULT));
        if (filename == null) {
        	// default instructions
        	centerString(gc, "To start, select a skill level.", 250);
        	centerString(gc, "(Press a number from 0 to 9,", 300);
        	centerString(gc, "or a letter from a to f)", 320);
        }
        else {
        	// message if maze is loaded from file
        	centerString(gc, "Loading maze from file:", 250);
        	centerString(gc, filename, 300);
        }
        centerString(gc, "Version 4.2", 350);
    }
    /**
     * Updates the font and color settings of the given graphics object
     * @param gc the object to draw on
     * @param font the font to use for drawing
     * @param mcolor the color to use
     */
	private void updateFontAndColor(MazePanel gc, Font font, MazeColors mcolor) {
		gc.setFont(font);
        gc.setColor(ColorTheme.getColor(mcolor));
	}
    /**
     * Draws the background, a green and cold frame with
     * a white center stage area
     * @param gc the graphics to draw on
     */
	private void drawBackground(MazePanel gc) {
		gc.setColor(ColorTheme.getColor(MazeColors.FRAME_OUTSIDE));
        gc.addFilledRectangle(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
        gc.setColor(ColorTheme.getColor(MazeColors.FRAME_MIDDLE));
        gc.addFilledRectangle(10, 10, Constants.VIEW_WIDTH-20, Constants.VIEW_HEIGHT-20);
        gc.setColor(ColorTheme.getColor(MazeColors.FRAME_INSIDE));
        gc.addFilledRectangle(15, 15, Constants.VIEW_WIDTH-30, Constants.VIEW_HEIGHT-30);
	}

    /**
     * Draws the finish screen, screen content is hard coded
     * @param panel holds the graphics for the off-screen image
     */
	void redrawFinish(MazePanel panel) {
        if (null == panel) {
        	LOGGER.warning(errorMsg) ;
        }
        else {
            redrawFinish(panel);
        }
	}
	/**
	 * Helper method for redraw to draw final screen, screen is hard coded
	 * @param gc graphics is the off-screen image
	 */
	private void redrawFinish(MazePanel gc) {
		// produce blue background
		drawBackground(gc);
		// write the title 
		updateFontAndColor(gc, largeBannerFont, MazeColors.TITLE_LARGE);
		gc.addMarker(gc.getWidth(),100,"You won!");
		// write some extra blurb
		updateFontAndColor(gc, smallBannerFont, MazeColors.TITLE_SMALL);
		gc.addMarker(gc.getWidth(), 160, "Congratulations!");
		// write the instructions
		gc.setColor(ColorTheme.getColor(MazeColors.TITLE_DEFAULT));
		gc.addMarker(gc.getWidth(),300, "Hit any key to restart");
	}
    /**
     * Draws the generating screen, screen content is hard coded
     * @param panel holds the graphics for the off-screen image
     * @param percentDone is the percentage of work done so far
     */
    public void redrawGenerating(MazePanel panel, int percentDone) {
        if (null == panel) {
        	LOGGER.warning(errorMsg) ;
        }
        else {
            redrawGenerating(panel, percentDone);
        }
    }
	/**
	 * Helper method for redraw to draw screen during phase of maze generation,
	 * screen is hard coded, only percentage is dynamic
	 * @param gc graphics is the off screen image
	 * @param percentage is the percentage of progress to show
	 */
	private void redrawGenerating(MazePanel gc, int percentage) {
		// produce  background and  title
		drawBackground(gc);
		updateFontAndColor(gc, largeBannerFont, MazeColors.TITLE_LARGE);
		centerString(gc, "Building maze", 150);
		// show progress
		updateFontAndColor(gc, smallBannerFont, MazeColors.TITLE_SMALL);
		centerString(gc, percentage + "% completed", 200);
		// write the instructions
		gc.setColor(ColorTheme.getColor(MazeColors.TITLE_DEFAULT));
		centerString(gc, "Hit escape to stop", 300);
	}
	
	private void centerString(MazePanel g, String str, int ypos) {
		g.addMarker(str,
				(Constants.VIEW_WIDTH-g.getFontMetrics().stringWidth(str))/2, 
				ypos);
	}

	final Typeface largeBannerFont = new Typeface("TimesRoman", Typeface.BOLD, 48);
	final Font smallBannerFont = new Font("TimesRoman", Font.BOLD, 16);

}
