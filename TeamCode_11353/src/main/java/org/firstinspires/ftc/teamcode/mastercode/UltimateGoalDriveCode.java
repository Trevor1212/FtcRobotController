package org.firstinspires.ftc.teamcode.mastercode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


/**
     * This is the Main Driver Control for the 2020 LCL Lightning team 11353 Robot (unnamed)
     */

    @TeleOp(name="UltimateGoalDriveCode", group="Iterative Opmode")

    public class UltimateGoalDriveCode extends OpMode
    {
        private ElapsedTime runtime = new ElapsedTime();


        //Creates new robot
        UltimategoalHardware robot       = new UltimategoalHardware();

        /**
         * Code to run ONCE when the driver hits INIT
         */
        @Override
        public void init() {
            telemetry.addData("Status", "Initialized");

            //Initalize hardware from Hardware UltimateGoal
            robot.init(hardwareMap);


            // Tell the driver that initialization is complete.
            telemetry.addData("Status", "Initialized");
        }

        /**
         * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
         */
        @Override
        public void init_loop() {


            /**
             * Code to run ONCE when the driver hits PLAY
             */
        }
        @Override
        public void start() {
            robot.drop.setPosition(.7);

        }

        /**
         * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
         */



        @Override
        public void loop() {

            /**
             * Gets z Values - Right-Handed Coordinate System
             */

            robot.drop.setPosition(0);
           // telemetry.addData("Front Distance", robot.dSensorFront.getDistance(DistanceUnit.INCH));
            //telemetry.addData("Back Distance", robot.dSensorBack.getDistance(DistanceUnit.INCH));

            //Double Variables for driver control sticks
            double x = -gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double z = -gamepad1.right_stick_x;



            if (gamepad1.left_bumper) { // Control Speed of Drive
                robot.speedFactor = 3;
            } else {
                robot.speedFactor = 1;
            }

            if (gamepad1.right_bumper) { //Reverse drive control
                robot.reverseFactor = -1;
            } else {
                robot.reverseFactor = 1;
            }

            //DRIVE FUNCTION BELOW
            robot.frontleftDrive.setPower((Math.pow((y + x) * robot.reverseFactor + z, 3)) / robot.speedFactor);
            robot.frontrightDrive.setPower((Math.pow((-y + x) * robot.reverseFactor + z, 3)) / robot.speedFactor);
            robot.backleftDrive.setPower((Math.pow((y - x) * robot.reverseFactor + z, 3)) / robot.speedFactor);
            robot.backrightDrive.setPower((Math.pow((-y - x) * robot.reverseFactor + z, 3)) / robot.speedFactor);


            //Intake drive
            if (robot.intakeToggle && gamepad1.y) {  // Only execute once per Button push
                robot.intakeToggle = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
                if (robot.intake) {  // Decide which way to set the motor this time through (or use this as a motor value instead)
                    robot.intake = false;
                    robot.intakeChainDrive.setPower(0);
                    telemetry.addData("Intake", "Deactivated");
                    robot.intakeToggle = true;
                } else {
                    robot.intake = true;
                    robot.intakeToggle = true;
                    robot.intakeChainDrive.setPower(1);
                    telemetry.addData("Intake", "Activated");
                }
            }

            //Shooter code

            if (robot.shooterToggle && gamepad1.right_bumper) {  // Only execute once per Button push
                robot.shooterToggle = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
                if (robot.shooter) {  // Decide which way to set the motor this time through (or use this as a motor value instead)
                    robot.intake = false;
                    robot.shooterDrive.setPower(0);
                    telemetry.addData("Shooter", "Deactivated");
                    robot.shooterToggle = true;
                } else {
                    robot.shooter = true;
                    robot.shooterToggle = true;
                    robot.shooterDrive.setPower(1);
                    telemetry.addData("Shooter", "Activated");
                }
            }

            // Trigger Mechanism
            if (robot.triggerboolean && gamepad1.b) {  // Only execute once per Button push
                robot.triggerboolean = false;
                // Prevents this section of code from being called again until the Button is released and re-pressed
                if (robot.triggerstate) {  // Decide which way to set the motor this time through (or use this as a motor value instead)
                    robot.triggerstate = false;
                    robot.trigger.setPosition(.43);
                    telemetry.addData("TRIGGER", "Deactivated");
                    robot.triggerboolean = true;
                } else {
                    if (robot.waittime > System.currentTimeMillis()) {
                        robot.trigger.setPosition(.43);
                        robot.waittime = 0;
                        return;

                    } else {
                        robot.trigger.setPosition(.53);
                        telemetry.addData("Intake", "Activated");
                        if (robot.waittime == 0) {
                            robot.waittime = System.currentTimeMillis() + 84;
                        }
                        robot.triggerstate = true;
                        robot.triggerboolean = true;
                    }

                }
            }
            telemetry.update();
        }



            /*
            if(gamepad1.b){
                robot.trigger.setPosition(.53);

            }
            if(gamepad1.a){
                robot.trigger.setPosition(.43);
            }
*/



        @Override
        public void stop() {
        }

    }

