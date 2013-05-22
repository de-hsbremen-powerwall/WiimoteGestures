import java.awt.Color;
import java.util.ArrayList;

import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;


public class Player implements WiimoteListener{
	
	private Color playerColor;
	
	private int pointer_x = 0;
	private int pointer_y = 0;
	private float pointer_z = 0;
	
	private float pitch = 0.0f;
	private float roll = 0.0f;
	private float yaw = 0.0f;
	
	private ArrayList<Float> acc_X;
	private ArrayList<Float> acc_Y;
	private ArrayList<Float> acc_Z;

	private boolean buttonAPressed = false;
	private boolean buttonBPressed = false;
	private boolean buttonOnePressed = false;
	private boolean buttonTwoPressed = false;
	private boolean buttonUpPressed = false;
	private boolean buttonDownPressed = false;
	private boolean buttonLeftPressed = false;
	private boolean buttonRightPressed = false;
	private boolean buttonMinusPressed = false;
	private boolean buttonPlusPressed = false;
	
	private boolean motionX = false;
	private boolean motionY = false;
	private boolean motionZ = false;
	
	public Player(int id){
		switch(id){
			case 1: playerColor = Color.RED; break;
			case 2: playerColor = Color.ORANGE; break;
			case 3: playerColor = Color.GREEN; break;
			case 4: playerColor = Color.PINK; break;
		}
		
		acc_X = new ArrayList<Float>();
		acc_Y = new ArrayList<Float>();
		acc_Z = new ArrayList<Float>();
	}
	
	public void resetAllButtons(){
		buttonAPressed = false;
		buttonBPressed = false;
		buttonOnePressed = false;
		buttonTwoPressed = false;
		buttonUpPressed = false;
		buttonDownPressed = false;
		buttonLeftPressed = false;
		buttonRightPressed = false;
		buttonMinusPressed = false;
		buttonPlusPressed = false;
		
		motionX = false;
		motionY = false;
		motionZ = false;
	}
	
	public int getX(){
		return pointer_x;
	}
	
	public int getY(){
		return pointer_y;
	}
	
	public float getZ(){
		return pointer_z;
	}
	
	public Color getPlayerColor(){
		return playerColor;
	}
	
	public boolean isAPressed() {
		return buttonAPressed;
	}
	
	public void setAPressed(boolean aPressed) {
		this.buttonAPressed = aPressed;
	}
	
	public boolean isBPressed() {
		return buttonBPressed;
	}
	
	public void setBPressed(boolean bPressed) {
		this.buttonBPressed = bPressed;
	}
	
	public boolean isOnePressed() {
		return buttonOnePressed;
	}
	
	public void setOnePressed(boolean oPressed) {
		this.buttonOnePressed = oPressed;
	}
	
	public boolean isTwoPressed() {
		return buttonTwoPressed;
	}
	
	public void setTwoPressed(boolean tPressed) {
		this.buttonTwoPressed = tPressed;
	}
	
	public boolean isUpPressed() {
		return buttonUpPressed;
	}
	
	public void setUpPressed(boolean uPressed) {
		this.buttonUpPressed = uPressed;
	}
	
	public boolean isDownPressed() {
		return buttonDownPressed;
	}
	
	public void setDownPressed(boolean dPressed) {
		this.buttonDownPressed = dPressed;
	}
	
	public boolean isLeftPressed() {
		return buttonLeftPressed;
	}
	
	public void setLeftPressed(boolean lPressed) {
		this.buttonLeftPressed = lPressed;
	}
	
	public boolean isRightPressed() {
		return buttonRightPressed;
	}
	
	public void setRightPressed(boolean rPressed) {
		this.buttonRightPressed = rPressed;
	}
	
	public boolean isMinusPressed() {
		return buttonMinusPressed;
	}
	
	public void setMinusPressed(boolean mPressed) {
		this.buttonMinusPressed = mPressed;
	}
	
	public boolean isPlusPressed() {
		return buttonPlusPressed;
	}
	
	public void setPlusPressed(boolean pPressed) {
		this.buttonPlusPressed = pPressed;
	}
	
	public float getPitch(){
		return pitch;
	}
	
	public float getRoll(){
		return roll;
	}
	
	public float getYaw(){
		return yaw;
	}
	
	public boolean isMotionXActive() {
		return motionX;
	}
	
	public void setMotionXActive(boolean mxPressed) {
		this.motionX = mxPressed;
	}
	
	public boolean isMotionYActive() {
		return motionY;
	}
	
	public void setMotionYActive(boolean myPressed) {
		this.motionY = myPressed;
	}
	
	public boolean isMotionZActive() {
		return motionZ;
	}
	
	public void setMotionZActive(boolean mzPressed) {
		this.motionZ = mzPressed;
	}


	@Override
	public void onButtonsEvent(WiimoteButtonsEvent arg0) {
		if(arg0.isButtonAJustPressed()) {
			setAPressed(true);
		}
		if(arg0.isButtonBJustPressed()) {
			setBPressed(true);
		}	
		if(arg0.isButtonOneJustPressed()) {
			setOnePressed(true);
		}
		if(arg0.isButtonTwoJustPressed()) {
			setTwoPressed(true);
		}
		if(arg0.isButtonUpJustPressed()) {
			setUpPressed(true);
		}
		if(arg0.isButtonDownJustPressed()) {
			setDownPressed(true);
		}
		if(arg0.isButtonLeftJustPressed()) {
			setLeftPressed(true);
		}
		if(arg0.isButtonRightJustPressed()) {
			setRightPressed(true);
		}
		if(arg0.isButtonMinusJustPressed()) {
			setMinusPressed(true);
		}
		if(arg0.isButtonPlusJustPressed()) {
			setPlusPressed(true);
		}
	}
	
	@Override
	public void onIrEvent(IREvent arg0) {
		pointer_x = arg0.getX();
    	pointer_y = arg0.getY();
    	pointer_z = arg0.getDistance();
	}

	@Override
	public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnectionEvent(DisconnectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExpansionEvent(ExpansionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMotionSensingEvent(MotionSensingEvent arg0) {
		// TODO Auto-generated method stub
		pitch = arg0.getOrientation().getPitch();
		roll = arg0.getOrientation().getRoll();
		yaw = arg0.getOrientation().getYaw();
		
		acc_X.add(arg0.getGforce().getX());
		acc_Y.add(arg0.getGforce().getY());
		acc_Z.add(arg0.getGforce().getZ());
		
		while(acc_X.size() > 7)
			acc_X.remove(0);
		while(acc_Y.size() > 7)
			acc_Y.remove(0);
		while(acc_Z.size() > 7)
			acc_Z.remove(0);
		
		if(acc_X.size() == 7 && (acc_X.get(0) < acc_X.get(1)) && (acc_X.get(1) < acc_X.get(2)) && (acc_X.get(2) < acc_X.get(3)) && 
		  (acc_X.get(3) < acc_X.get(4)) && (acc_X.get(4) < acc_X.get(5)) && (acc_X.get(5) < acc_X.get(6)) && (acc_X.get(6) > 2.5f) ){
			setMotionXActive(true);
			acc_X = new ArrayList<Float>();
		}
		if(acc_Y.size() == 7 && (acc_Y.get(0) < acc_Y.get(1)) && (acc_Y.get(1) < acc_Y.get(2)) && (acc_Y.get(2) < acc_Y.get(3)) && 
			(acc_Y.get(3) < acc_Y.get(4)) && (acc_Y.get(4) < acc_Y.get(5)) && (acc_Y.get(5) < acc_Y.get(6)) && (acc_Y.get(6) > 2.5f) ){
			setMotionYActive(true);
			acc_Y = new ArrayList<Float>();
		}
		if(acc_Z.size() == 7 && (acc_Z.get(0) < acc_Z.get(1)) && (acc_Z.get(1) < acc_Z.get(2)) && (acc_Z.get(2) < acc_Z.get(3)) && 
			(acc_Z.get(3) < acc_Z.get(4)) && (acc_Z.get(4) < acc_Z.get(5)) && (acc_Z.get(5) < acc_Z.get(6)) && (acc_Z.get(6) > 2.5f) ){
			setMotionZActive(true);
			acc_Z = new ArrayList<Float>();
		}
		
	}

	@Override
	public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusEvent(StatusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
