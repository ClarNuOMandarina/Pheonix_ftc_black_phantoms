//package org.firstinspires.ftc.teamcode.user;
//
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.ParallelAction;
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.ProfileAccelConstraint;
//import com.acmerobotics.roadrunner.SequentialAction;
//import com.acmerobotics.roadrunner.SleepAction;
//import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
//import com.acmerobotics.roadrunner.TrajectoryBuilder;
//import com.acmerobotics.roadrunner.TranslationalVelConstraint;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.teamcode.MecanumDrive;
//
//@Autonomous(name="auto 4 spec expermiental")
//public class auto_4_spec_experimental extends LinearOpMode {
//
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        Pose2d initialPose = new Pose2d(new Vector2d(7.2,-62), Math.toRadians(-90));
//
//
//        MecanumDrive drive = new MecanumDrive(hardwareMap,initialPose);
//        colection colection = new colection(hardwareMap);
//        extension extension = new extension(hardwareMap);
//        scoring scoring = new scoring(hardwareMap);
//        slides slides = new slides(hardwareMap);
//        slides.slide_init();
//        colection.init_config();
//        extension.extend(extension.extension_retracted);
//        scoring.scoring_arm_score_specimen_score();
//        scoring.gripper_grab();
//        slides.reset_encoder();
//        TrajectoryActionBuilder start_to_score = drive.actionBuilder(initialPose)
//                .lineToY(-37);
//        TrajectoryActionBuilder score_to_transfer = drive.actionBuilder(new Pose2d(new Vector2d(7.2,-36),Math.toRadians(-90)))
//                .strafeTo(new Vector2d(32,-37));
//        TrajectoryActionBuilder transfer_sample_1 = drive.actionBuilder(new Pose2d(new Vector2d(32,-37),Math.toRadians(-90)))
//                .strafeToLinearHeading(new Vector2d(48,-41.5),Math.toRadians(90));
//        TrajectoryActionBuilder transfer_sample_1_finish = drive.actionBuilder(new Pose2d(new Vector2d(48,-41.5),Math.toRadians(90)))
//                .strafeToLinearHeading(new Vector2d(50.5,-49),Math.toRadians(-62));
//
//
//
//        TrajectoryActionBuilder transfer_sample_2 = drive.actionBuilder(new Pose2d(new Vector2d(50.5,-49),Math.toRadians(-62)))
//                .strafeToLinearHeading(new Vector2d(58,-42),Math.toRadians(90));
//        TrajectoryActionBuilder transfer_sample_2_finish = drive.actionBuilder(new Pose2d(new Vector2d(58,-42),Math.toRadians(90)))
//                .strafeToLinearHeading(new Vector2d(57,-48),Math.toRadians(250));
//        TrajectoryActionBuilder transfer_sample_3 = drive.actionBuilder(new Pose2d(new Vector2d(57,-48),Math.toRadians(250)))
//                        .strafeToLinearHeading(new Vector2d(56,-26.4),Math.toRadians(0));
////        TrajectoryActionBuilder transfer_sample_3 = drive.actionBuilder(new Pose2d(new Vector2d(57,-48),Math.toRadians(241)))
////                        .strafeToLinearHeading(new Vector2d(55.5,-26),Math.toRadians(0));
//
//        TrajectoryActionBuilder transfer_sample_3_finish = drive.actionBuilder(new Pose2d(new Vector2d(56,-26.4),Math.toRadians(0)))
//                .strafeToLinearHeading(new Vector2d(57,-48),Math.toRadians(241));
//
//        TrajectoryActionBuilder specimen_collect_pre = drive.actionBuilder(new Pose2d(new Vector2d(57,-48),Math.toRadians(246)))
//                .strafeToLinearHeading(new Vector2d(38,-45),Math.toRadians(84));
//        TrajectoryActionBuilder specimen_collect_pre_cicling = drive.actionBuilder(new Pose2d(new Vector2d(10,-48),Math.toRadians(-90)))
//                .strafeToLinearHeading(new Vector2d(38,-45),Math.toRadians(84));
//        TrajectoryActionBuilder specimen_collect = drive.actionBuilder(new Pose2d(new Vector2d(38,-45.5),Math.toRadians(84)))
//                .lineToY(-62);
//
//        TrajectoryActionBuilder scoring_poz_pre = drive.actionBuilder(new Pose2d(new Vector2d(38,-62),Math.toRadians(84)))
//                .afterTime(0.2,scoring.specimen_score())
//                .strafeToLinearHeading(new Vector2d(12,-38),Math.toRadians(-94));
//        TrajectoryActionBuilder scoring_poz = drive.actionBuilder(new Pose2d(new Vector2d(12,-38),Math.toRadians(-94)))
//                .afterTime(0.1,slides.specimen_score_high())
//                .lineToY(-35);
//        TrajectoryActionBuilder scoring_poz_pre_2 = drive.actionBuilder(new Pose2d(new Vector2d(26.5,-59.5),Math.toRadians(84)))
//
//                .afterTime(0.2,scoring.specimen_score())
//                .strafeToLinearHeading(new Vector2d(11,-42),Math.toRadians(-94));
//        TrajectoryActionBuilder scoring_poz_2 = drive.actionBuilder(new Pose2d(new Vector2d(11,-42),Math.toRadians(-94)))
//                .afterTime(0.1,slides.specimen_score_high())
//
//                .lineToY(-35);
//        TrajectoryActionBuilder scoring_poz_pre_3 = drive.actionBuilder(new Pose2d(new Vector2d(26.5,-59.5),Math.toRadians(84)))
//                .afterTime(0.1,scoring.specimen_score())
//                .strafeToLinearHeading(new Vector2d(6,-42),Math.toRadians(-94));
//        TrajectoryActionBuilder scoring_poz_3 = drive.actionBuilder(new Pose2d(new Vector2d(6,-42),Math.toRadians(-94)))
//                .afterTime(0.1,slides.specimen_score_high())
//                .lineToY(-35);
//        TrajectoryActionBuilder parking = drive.actionBuilder(new Pose2d(new Vector2d(4,-37),Math.toRadians(-37)))
//                .afterTime(0.5,extension.max_extension())
//                .afterDisp(0.1,scoring.auto_End())
//                .afterDisp(0.1,colection.auto_end())
//                .afterDisp(0.1,slides.slide_init())
//                .strafeToLinearHeading(new Vector2d(26,-49),Math.toRadians(-29));
//
//        scoring.gripper(scoring.gripper_hold);
//
//        waitForStart();
//        if (isStopRequested()) return;
//        scoring.specimen_score();
//        slides.culisante(slides.slides_specimen_high);
//        Actions.runBlocking(
//                new SequentialAction(
//                        start_to_score.build()
//                ));
//        slides.culisante(slides.slides_specimen_high_score);
//        sleep(400);
//        scoring.gripper(scoring.gripper_release);
//        sleep(200);
//        slides.culisante(slides.slides_init);
//        colection.colection_arm(colection.colection_default);
//        colection.default_config();
//        Actions.runBlocking(
//                new SequentialAction(
//                        score_to_transfer.build(),
//                        transfer_sample_1.build()
//                ));
//        colection.colection_arm(colection.colection_extended_auto);
//        sleep(200);
//        colection.gripper_grab();
//        sleep(300);
//        colection.colection_arm(colection.colection_default);
//        Actions.runBlocking(
//                new SequentialAction(
//                        transfer_sample_1_finish.build()
//                ));
//        colection.gripper_release();
//        sleep(200);
//        Actions.runBlocking(
//                new SequentialAction(
//                        transfer_sample_2.build()
//                ));
//        colection.colection_arm(colection.colection_extended_auto);
//        sleep(200);
//        colection.gripper_grab();
//        sleep(300);
//        colection.colection_arm(colection.colection_default);
//        Actions.runBlocking(
//                new SequentialAction(
//                        transfer_sample_2_finish.build()
//                ));
//        colection.gripper_release();
//        sleep(200);
//
//        colection.gripper_angle.setPosition(colection.gripper_angle_vertical);
//        Actions.runBlocking(
//                new SequentialAction(
//                        transfer_sample_3.build()
//                ));
//        colection.colection_arm(colection.colection_extended_auto);
//        sleep(200);
//        colection.gripper_grab();
//        sleep(300);
//        colection.colection_arm(colection.colection_default);
//        Actions.runBlocking(
//                new SequentialAction(
//                        transfer_sample_3_finish.build()
//                ));
//        colection.gripper_release();
//        sleep(200);
//
//
//
//        Actions.runBlocking(
//                new SequentialAction(
//                        scoring.specimen_collect(),
//                        specimen_collect_pre.build(),
//
//                        specimen_collect.build()
//                ));
//        sleep(200);
//        scoring.gripper(scoring.gripper_hold);
//        sleep(200);
//        slides.culisante(slides.slides_specimen_high+300);
//        Actions.runBlocking(
//                new SequentialAction(
//                        scoring_poz_pre.build()
//                ));
//        scoring.scoring_arm_score_specimen_score();
//        Actions.runBlocking(
//                new SequentialAction(
//                        scoring_poz.build()
//                ));
//        telemetry.addData("CULI",slides.left_slide.getCurrentPosition());
//        telemetry.addData("CULI2",slides.right_slide.getCurrentPosition());
//        telemetry.addData("extend",extension.left_extension.getPosition());
//        telemetry.update();
//        slides.culisante(slides.slides_specimen_high_score);
//        sleep(400);
//        scoring.gripper(scoring.gripper_release);
//        sleep(200);
//
//        // ciclu 2
//
//
//
//        Actions.runBlocking(
//                new SequentialAction(
//                        specimen_collect_pre_cicling.build()
//                ));
//        slides.culisante(slides.slides_init);
//        scoring.scoring_arm_score_specimen_collect();
//        Actions.runBlocking(
//                new SequentialAction(
//                        specimen_collect.build()
//
//                ));
//        sleep(200);
//        scoring.gripper(scoring.gripper_hold);
//        sleep(200);
//        slides.culisante(slides.slides_specimen_high+300);
//
//        Actions.runBlocking(
//                new SequentialAction(
//                        scoring_poz_pre_2.build(),
//                        scoring_poz_2.build()
//                ));
//        telemetry.addData("CULI",slides.left_slide.getCurrentPosition());
//        telemetry.addData("CULI2",slides.right_slide.getCurrentPosition());
//        telemetry.addData("extend",extension.left_extension.getPosition());
//        telemetry.update();
//        slides.culisante(slides.slides_specimen_high_score);
//        sleep(400);
//        scoring.gripper(scoring.gripper_release);
//        sleep(200);
//        Actions.runBlocking(
//                new SequentialAction(
//                        specimen_collect_pre_cicling.build()
//                ));
//        slides.culisante(slides.slides_init);
//        scoring.scoring_arm_score_specimen_collect();
//        Actions.runBlocking(
//                new SequentialAction(
//                        specimen_collect.build()
//
//                ));
//        sleep(200);
//        scoring.gripper(scoring.gripper_hold);
//        sleep(200);
//        slides.culisante(slides.slides_specimen_high+300);
//
//        Actions.runBlocking(
//                new SequentialAction(
//                        scoring_poz_pre_3.build(),
//                        scoring_poz_3.build()
//                ));
//        telemetry.addData("CULI",slides.left_slide.getCurrentPosition());
//        telemetry.addData("CULI2",slides.right_slide.getCurrentPosition());
//        telemetry.addData("extend",extension.left_extension.getPosition());
//        telemetry.update();
//        slides.culisante(slides.slides_specimen_high_score);
//        sleep(400);
//        scoring.gripper(scoring.gripper_release);
//        sleep(200);
//
//        Actions.runBlocking(
//                new SequentialAction(
//                        parking.build()
//                ));
//        while(!isStopRequested()){
//            scoring.scoring_arm_auto_init_end();
//            scoring.grip_transfer_release();
//            slides.culisante(slides.slides_init);
//            colection.init_config();
//        }
////        Actions.runBlocking(
////                new SequentialAction(
////                        start_to_score.build(),
////                        scoring.gripper_release(),
////                        slides.slide_init(),
////                        scoring.specimen_collect(),
////
////
////                        score_to_transfer.build(),
////                        transfer_sample_1.build(),
////                        transfer_sample_1_finish.build(),
////                        transfer_sample_2_pre.build(),
////                        transfer_sample_2.build(),
////                        transfer_sample_2_finish.build(),
////
////                        //first cycle
////
////                        specimen_collect.build(),
////                        scoring.gripper_grab(),
////                        slides.specimen_score(),
////
////                        scoring_poz.build(),
////                        scoring.gripper_release(),
////                        slides.slide_init(),
////                        scoring.specimen_collect(),
////
////
////                        specimen_collect.build(),
////                        scoring.gripper_grab(),
////                        slides.specimen_score(),
////
////                        scoring_poz.build(),
////                        scoring.gripper_release(),
////                        slides.slide_init(),
////                        scoring.init_config_auto(),
////
////                        parking.build()
////                ));
//
//        telemetry.update();
//    }
//}
