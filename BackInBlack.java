package BackInBlack;

import robocode.*;
import java.awt.*;


public class BackInBlack extends AdvancedRobot {
    int moveDirection=1;//para onde ir
   
    public void run() {
        setAdjustRadarForRobotTurn(true);//travar o radar enquanto o robo vira
        setBodyColor(Color.black);
        setGunColor(Color.gray);
        setRadarColor(Color.black);
        setScanColor(Color.gray);
        setBulletColor(Color.orange);
        setAdjustGunForRobotTurn(true); // travar a arma enquanto vira
        turnRadarRightRadians(Double.POSITIVE_INFINITY);//continuar virando o radar pra direita
    }

    //ao escanear um robo
    public void onScannedRobot(ScannedRobotEvent e) {
        double absBearing=e.getBearingRadians()+getHeadingRadians();//posicao absoluta do inimigo
        double latVel=e.getVelocity() * Math.sin(e.getHeadingRadians() -absBearing);//velocidade do inimigo
        double gunTurnAmt;//quanto virar a arma
        setTurnRadarLeftRadians(getRadarTurnRemainingRadians());//trava o radar
        if(Math.random()>.9){
            setMaxVelocity((12*Math.random())+12);//mudar velocidade aleatoriamente
        }
        if (e.getDistance() > 150) {//se distancia maior que 150
            gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/22);//quanto virar a arma
            setTurnGunRightRadians(gunTurnAmt); //virar a arma
            setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absBearing-getHeadingRadians()+latVel/getVelocity()));//ir em direçao a posição futura do inimigo
            setAhead((e.getDistance() - 140)*moveDirection);//pra frente
            setFire(10);//fuego
        }
        else{//se estiver perto o bastante
            gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/15);//quanto virar a arma com um pouco de previsao
            setTurnGunRightRadians(gunTurnAmt);//virar arma
            setTurnLeft(-90-e.getBearing()); //virar perpendicularmente pro inimigo
            setAhead((e.getDistance() - 140)*moveDirection);//pra frente
            setFire(10);//fuego
        }
    }
    public void onHitWall(HitWallEvent e){
        moveDirection=-moveDirection;//voltar se acertar parede
    }
    /**
     * dancinha da vitoria
     */
    public void onWin(WinEvent e) {
        for (int i = 0; i < 50; i++) {
            turnRight(30);
            turnLeft(30);
        }
    }
}