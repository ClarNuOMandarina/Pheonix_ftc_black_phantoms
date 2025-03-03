package org.firstinspires.ftc.teamcode.user;


import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class scoring {
    // declarare motoare utilizate
    public Servo scoring_arm_left;
    public Servo scoring_arm_right;
    public Servo grip_transfer;
    // declarare/memorare pozitii utilizate
    public double gripper_hold=0.59;
    public double gripper_semi_hold=0.565;
    public double gripper_release=0.42  ;
    public double scoring_arm_left_colect=0.55;
    public double scoring_arm_right_colect=0.1;
    public double scoring_arm_left_default=0.55;
    public double scoring_arm_right_default=0.1;
    public double scoring_arm_left_basket=0.2;
    public double scoring_arm_right_basket=0.2  ;
    public double scoring_arm_left_specimen_score=0.3 ;
    public double scoring_arm_right_specimen_score=0.3 ;
    public double scoring_arm_left_specimen_first_cycle=0.6 ;
    public double scoring_arm_right_specimen_first_cycle=0.6 ;
    public double scoring_arm_left_specimen_prepare=0.56 ;
    public double scoring_arm_right_specimen_prepare=0.58 ;

    public double scoring_arm_left_auto_end_init=0.3 ;
    public double scoring_arm_right_auto_end_init=0.3 ;
    public double scoring_arm_left_auto_park=0.3 ;
    public double scoring_arm_right_auto_park=0.3 ;
    public double scoring_arm_left_specimen_collect=0;
    public double scoring_arm_right_specimen_collect=0;
 public double scoring_arm_left_auto_init=0.04;
    public double scoring_arm_right_auto_init=0.34;
    public double scoring_arm_left_auto_park_basket=0.05 ;
    public double scoring_arm_right_auto_park_basket=0.05 ;

    public scoring(HardwareMap hardwareMap){
        // detalierea modului de functionare a mecanismelor
        scoring_arm_left=hardwareMap.get(Servo.class,"scoring_arm_left");
        scoring_arm_right=hardwareMap.get(Servo.class,"scoring_arm_right");
        grip_transfer=hardwareMap.get(Servo.class,"grip_transfer");

    }
    public void score(double x,double y){
        scoring_arm_left.setPosition(x);
        scoring_arm_right.setPosition(y);
    }
    public void init_auto_arms(){
        scoring_arm_left.setPosition(scoring_arm_left_auto_init);
        scoring_arm_right.setPosition(scoring_arm_right_auto_init);
    }
    public void park_basket(){
        scoring_arm_left.setPosition(scoring_arm_left_auto_park_basket);
        scoring_arm_right.setPosition(scoring_arm_right_auto_park_basket);
        grip_transfer.setPosition(gripper_hold);
    }
    public void scoring_arm_colect(){
        scoring_arm_left.setPosition(scoring_arm_left_colect);
        scoring_arm_right.setPosition(scoring_arm_right_colect);
    }
    public void scoring_arm_first_cycle(){
        scoring_arm_left.setPosition(scoring_arm_left_specimen_first_cycle);
        scoring_arm_right.setPosition(scoring_arm_right_specimen_first_cycle);
    }
    public void scoring_arm_auto_init_end(){
        scoring_arm_left.setPosition(scoring_arm_left_auto_end_init);
        scoring_arm_right.setPosition(scoring_arm_right_auto_end_init);
    }
    public void scoring_arm_specimen_prepare(){
        scoring_arm_left.setPosition(scoring_arm_left_specimen_prepare);
        scoring_arm_right.setPosition(scoring_arm_right_specimen_prepare);
    }
//    public void scoring_arm_specimen_score_auto(){
//        scoring_arm_left.setPosition(scoring_arm_left_specimen_score_auto);
//        scoring_arm_right.setPosition(scoring_arm_right_specimen_score_auto);
//    }
    public void scoring_arm_init(){
        scoring_arm_left.setPosition(scoring_arm_left_colect);
        scoring_arm_right.setPosition(scoring_arm_right_colect);
    }
    public void scoring_arm_default(){
        scoring_arm_left.setPosition(scoring_arm_left_default);
        scoring_arm_right.setPosition(scoring_arm_right_default);
    }
    public void scoring_arm_park(){
        scoring_arm_left.setPosition(scoring_arm_left_auto_park);
        scoring_arm_right.setPosition(scoring_arm_right_auto_park);
    }
    public void scoring_arm_score_basket(){
        scoring_arm_left.setPosition(scoring_arm_left_basket);
        scoring_arm_right.setPosition(scoring_arm_right_basket);
    }
    public void scoring_arm_score_specimen_score(){
        scoring_arm_left.setPosition(scoring_arm_left_specimen_score);
        scoring_arm_right.setPosition(scoring_arm_right_specimen_score);
    }
    public void scoring_arm_score_specimen_collect(){
        scoring_arm_left.setPosition(scoring_arm_left_specimen_collect);
        scoring_arm_right.setPosition(scoring_arm_right_specimen_collect);
    }
    public void gripper(double x){
        grip_transfer.setPosition(x);
    }
    public void grip_transfer_grab(){
        grip_transfer.setPosition(gripper_hold);
    }
    public void grip_transfer_release(){
        grip_transfer.setPosition(gripper_release);
    }
    public void init_config(){
        scoring_arm_left.setPosition(scoring_arm_left_default);
        scoring_arm_right.setPosition(scoring_arm_right_default);
        grip_transfer.setPosition(gripper_release);

    }
    public class Init_config  implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(true)

                init_config();

            return false;
        }

    }
    public Action init_config_auto(){
        return new Init_config();
    }
    public class Park_basket  implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                park_basket();

            return false;
        }

    }
    public Action park_basket_auto_new(){
        return new Park_basket();
    }
    public class First_cycle  implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(true)

                scoring_arm_first_cycle();

            return false;
        }

    }
    public Action first_cycle(){
        return new First_cycle();
    }


    public class Specimen_collect  implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(true)


                scoring_arm_score_specimen_collect();


            return false;
        }

    }
    public Action specimen_collect(){
        return new Specimen_collect();
    }
    public class Specimen_prepare  implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(true)


                scoring_arm_specimen_prepare();


            return false;
        }

    }
    public Action specimen_prepare(){
        return new Specimen_prepare();
    }

    public class Transfer  implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(true)


                scoring_arm_default();


            return false;
        }

    }
    public Action transfer(){
        return new Transfer();
    }
    public class Sample_collect  implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            if(true) scoring_arm_colect();

            return false;
        }

    }
    public Action sample_collect(){
        return new Sample_collect();
    }
    public class Sample_score  implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            if(true) scoring_arm_score_basket();


            return false;
        }

    }
    public Action sample_score(){
        return new Sample_score();
    }
    public class Specimen_score  implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(true) scoring_arm_score_specimen_score();
            return false;
        }

    }
    public Action specimen_score(){
        return new Specimen_score();
    }
    public class Gripper_grab  implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            gripper(gripper_hold);


            return false;
        }

    }
    public Action gripper_grab(){
        return new Gripper_grab();
    }
    public class Gripper_release  implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            gripper(gripper_release);


            return false;
        }

    }
    public Action gripper_release(){
        return new Gripper_release();
    }

    public class AUTO_RESET  implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(true) {
                scoring_arm_auto_init_end();
                grip_transfer_release();
            }
            return false;
        }

    }
    public Action auto_End(){
        return new scoring.AUTO_RESET();
    }
}
