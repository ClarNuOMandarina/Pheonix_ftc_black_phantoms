package org.firstinspires.ftc.teamcode.user;


import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class slides  {
    public DcMotorEx left_slide,right_slide;
    Servo hanging;

// pozitii culisanta
public double hang=1;
    public double not_hang=0.55;

    public int slides_init=0;
    public int slides_low_basket=1000;
    public int slides_high_basket=2480;
    public int slides_specimen_low=0;
    public int slides_specimen_high=1350;
    public int slides_specimen_high_score=1350  ;
    public int slides_specimen_high_score_tepeop=1350   ;
    public int slides_hang=1500;
    public int slides_auto_score=1350   ;
    public int slides_auto_park=500   ;
    public int slides_first_cycle=900;
    public int slide_auto_parking_new=770;

    public slides(HardwareMap hardwareMap){
        // declarari motoare si modul lor de functionare
        left_slide=hardwareMap.get(DcMotorEx.class,"left_slide");
        right_slide=hardwareMap.get(DcMotorEx.class,"right_slide");
        hanging=hardwareMap.get(Servo.class,"hang");
        right_slide.setDirection(DcMotorSimple.Direction.REVERSE);
        left_slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right_slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
hanging.setPosition(not_hang);
    }
    public void reset_encoder(){
        left_slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void culisante(int x)  {
        // functie generala pt uzul culisantelor
        left_slide.setTargetPosition(x);
        right_slide.setTargetPosition(x);
        left_slide.setPower(1);
        right_slide.setPower(1);
        left_slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right_slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void reset_culi_forced(){
        left_slide.setPower(-1);
        right_slide.setPower(-1);
    }

    public class Slide_init  implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            culisante(slides_init);


            return false;
        }

    }
    public Action slide_init(){
        return new Slide_init();
    }
    public class Slide_sample  implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            culisante(slides_high_basket);


            return false;
        }

    }
    public Action auto_park(){
        return new Auto_park();
    }
    public class Auto_park  implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            culisante(slides_auto_park);


            return false;
        }

    }
    public Action auto_score(){
        return new Auto_score();
    }
    public class Auto_park_basket_new  implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            culisante(slide_auto_parking_new);


            return false;
        }

    }
    public Action auto_park_basket_new(){
        return new Auto_park_basket_new();
    }
    public class Auto_score  implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            culisante(slides_specimen_high_score);


            return false;
        }

    }
    public Action slide_sample(){
        return new Slide_sample();
    }
    public class First_slide_cycle  implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            culisante(slides_first_cycle);


            return false;
        }

    }
    public Action first_slide_cycle(){
        return new First_slide_cycle();
    }
    public class Slide_specimen_score  implements Action{
        // actiune pentru a misca glisierele pe durata autonomiei(pe durata traiectorilor)
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            culisante(slides_specimen_high);
            return false;
        }

    }
    public Action specimen_score_high(){
        return new Slide_specimen_score();
    }


}
