import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main extends JFrame implements KeyListener{
	public JFrame arkaplan = new JFrame();
	public JLabel mainHero;
	
	public Main() {
		arkaplan.setDefaultCloseOperation(EXIT_ON_CLOSE);
		arkaplan.setBounds(0,0,500,500);
		arkaplan.setSize(500,500);
		arkaplan.setResizable(false);
		arkaplan.setLayout(null);
		arkaplan.addKeyListener(this);
		arkaplan.setVisible(true);
		mainHero = new JLabel();
		mainHero.setBounds(240,240,20,20);
		mainHero.setBackground(Color.green);
		mainHero.setOpaque(true);
		arkaplan.add(mainHero);
		arkaplan.setVisible(true);
	}
	
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 'w': {if(mainHero.getY()-10>=0) 
			mainHero.setLocation(mainHero.getX(), mainHero.getY()-10);
			break;
		}
		case 'a': {if(mainHero.getX()-10>=0)
			mainHero.setLocation(mainHero.getX()-10, mainHero.getY());
			break;
		}
		case 's':{ if(mainHero.getY()+10<=440)
			mainHero.setLocation(mainHero.getX(), mainHero.getY()+10);
			break;
		}
		case 'd':{ if(mainHero.getX()+10<=460)
			mainHero.setLocation(mainHero.getX()+10, mainHero.getY());
			break;
		}
	}
	}
	public void keyPressed(KeyEvent e) {
		
		 //	Turkce ve Ingilizce klavye kullananlar icin bu keyCode'lar degisecegi icin aciklama satiri icinde biraktim. 
		
/*
		switch (e.getKeyCode()) {
		case 87: mainHero.setLocation(mainHero.getX(), mainHero.getY()-10);
			break;
		case 65: mainHero.setLocation(mainHero.getX()-10, mainHero.getY());
			break;
		case 83: mainHero.setLocation(mainHero.getX(), mainHero.getY()+10);
			break;
		case 68: mainHero.setLocation(mainHero.getX()+10, mainHero.getY());
			break;
	}
*/
	}
	public void keyReleased(KeyEvent e) {
		
		//	KeyChar ve KeyCode tanimlamak icin buradan yardim aldim daha sonra aciklama satirina donusturdum.
		
		/*
		System.out.println("Klavyeden serbest birakilan keyCode:  " + e.getKeyCode());
		System.out.println("Klavyeden serbest birakilan keyChar:  " + e.getKeyChar());
		*/
	}
	
	class Monster extends Thread implements Runnable{
		public JLabel monster;
		
		public Monster(int x, int y) {
			monster = new JLabel();
			monster.setBounds(x%440, y%460, 20, 20);
			monster.setBackground(Color.blue);
			monster.setOpaque(true);
			arkaplan.add(monster);
		}
		public void run() {
			try {
				while(true) {
				int randomNumber=(int)Math.round(Math.random());
				if(randomNumber==0){
				if(mainHero.getX()>=monster.getX()) {
					monster.setLocation(monster.getX()+10,monster.getY());
				}
				if(mainHero.getX()<=monster.getX()) {
					monster.setLocation(monster.getX()-10,monster.getY());
				}
				}
			else{
				if(mainHero.getY()>=monster.getY()) {
					monster.setLocation(monster.getX(),monster.getY()+10);
				}
				if(mainHero.getY()<=monster.getY()) {
					monster.setLocation(monster.getX(),monster.getY()-10);
				}
			}
				if(new CollisionController().controller(20, 20, 20, 20, mainHero.getX(), mainHero.getY(), monster.getX(), monster.getY())) {
					System.out.println("Cikis yapildi. Temas var.");
					System.exit(0);
				}
				Thread.sleep(300);
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void currentState() {
			System.out.println("Monster'in X'i: " + monster.getX());
			System.out.println("Monster'in Y'si: " + monster.getY());
		}
		
	}
	class CollisionController {
	    public boolean controller(int widthOfMainHero, int heightOfMainHero, int widthOfMonster, int heightOfMonster, int xOfMainHero, int yOfMainHero, int xOfMonster, int yOfMonster) {

	        if (widthOfMonster<=0||heightOfMonster<= 0||widthOfMainHero<=0||heightOfMainHero<=0){
	            return false;
	        }
	        widthOfMonster+=xOfMonster; 
	        heightOfMonster+=yOfMonster;
	        widthOfMainHero+=xOfMainHero;
	        heightOfMainHero+=yOfMainHero;
	        return ((widthOfMonster<xOfMonster||widthOfMonster>xOfMainHero)&&(heightOfMonster<yOfMonster||heightOfMonster>yOfMainHero)&&(widthOfMainHero<xOfMainHero||widthOfMainHero>xOfMonster) &&(heightOfMainHero<yOfMainHero||heightOfMainHero>yOfMonster));
	    }
	}
	
	
	

	public static void main(String[] args) {
		
		int number_of_monsters =9;
		System.out.println(number_of_monsters);
		Main m = new Main();
		
		Main.Monster [] monsters = new Main.Monster[number_of_monsters];
		
		Random r = new Random();
		
		for(int i=0;i<number_of_monsters;i++)
		{
			monsters[i] = m.new Monster(Math.abs(r.nextInt()%500),Math.abs(r.nextInt()%500));		
		}
		
		for(int i=0;i<number_of_monsters;i++)
			monsters[i].start();
		
		try {
			for(int i=0;i<number_of_monsters;i++)
				monsters[i].join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}



}
