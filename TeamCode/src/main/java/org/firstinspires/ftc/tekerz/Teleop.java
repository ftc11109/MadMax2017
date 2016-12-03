/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.tekerz;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import static org.firstinspires.ftc.tekerz.Constants.*;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Tekerz: Teleop", group="Tekerz")
//@Autonomous(name="Tekerz: AutoByTime", group="Tekerz")



public class Teleop extends OpMode{

    /* Declare OpMode members. */
    Hardware robot       = new Hardware(); // use the class created to define a Pushbot's hardware
                                                         // could also use HardwarePushbotMatrix class.
    double rollerOffset = 0.0 ;                  // Servo mid position
    double directParicleOffset = 0.0 ;                  // Servo mid position
    boolean reversedDirection = false;

    // hsvValues is an array that will hold the hue, saturation, and value information.
    float hsvValues0[] = {0F,0F,0F};
    float hsvValues1[] = {0F,0F,0F};

    // values is a reference to the hsvValues array.
    final float values0[] = hsvValues0;
    final float values1[] = hsvValues1;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        robot.leftRoller.setPosition(LEFT_ROLLER_BASE);
        robot.rightRoller.setPosition(RIGHT_ROLLER_BASE);
        robot.directParticle.setPosition(DIRECT_PARTICLE_BASE);
        robot.shooterRight.setPower(0.0);
        robot.shooterLeft.setPower(0.0);

        robot.colorSensor0.enableLed(true);
        robot.colorSensor1.enableLed(true);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        /******************************************************************************************
         *                                THE DRIVER
         *                                USES GAMEPAD 2
         */

        /*********************************************************
         *      REVERSE     A / B     G2
         *      B sets the controls to normal
         *      A inverts the drive controls
         *      used for putting the robot in reverse drive
         */
        if (gamepad2.b) {
            reversedDirection = false;
        } else if (gamepad2.a) {
            reversedDirection = true;
        }


        /*********************************************************
         *      DRIVE      STICKS       G2
         *      drive with the sticks
         */

        // Run wheels in tank mode (note: The joystick goes negative when pushed forward)
        double left = deadZone(-gamepad2.left_stick_y);
        double right = deadZone(-gamepad2.right_stick_y);

        if (reversedDirection) {
            left = -left;
            right = -right;
        }

        robot.leftDrive.setPower(left);
        robot.rightDrive.setPower(right);


        /*********************************************************
         *      ROLLERS      LB / RB       G2
         *      toggle rollers open/close
         */
        if (gamepad2.right_bumper)
            rollerOffset += ROLLER_SPEED;
        else if (gamepad2.left_bumper)
            rollerOffset -= ROLLER_SPEED;
        // Move both servos to new position.  Assume servos are mirror image of each other.
        rollerOffset = Range.clip(rollerOffset, 0.0, 0.5);
        robot.leftRoller.setPosition(LEFT_ROLLER_BASE + rollerOffset);
        robot.rightRoller.setPosition(LEFT_ROLLER_BASE - rollerOffset);


        /*           manual adjustments for testing...
        if (gamepad1.right_bumper)
            rollerOffset += ROLLER_SPEED;
        else if (gamepad1.left_bumper)
            rollerOffset -= ROLLER_SPEED;
        // Move both servos to new position.  Assume servos are mirror image of each other.
        rollerOffset = Range.clip(rollerOffset, -0.5, 0.5);
        robot.leftRoller.setPosition(LEFT_ROLLER_BASE + rollerOffset);
        robot.rightRoller.setPosition(LEFT_ROLLER_BASE - rollerOffset);
        */




        /******************************************************************************************
         *                                THE CO-PILOT
         *                                USES GAMEPAD 1
         */


        if (gamepad1.right_trigger > 0.3) {
            robot.shooterClipRight.setPower(SHOOTER_CLIP_POWER);
            robot.shooterClipLeft.setPower(SHOOTER_CLIP_POWER);
        } else if (gamepad1.left_trigger > 0.3) {
            robot.shooterClipRight.setPower(-SHOOTER_CLIP_POWER);
            robot.shooterClipLeft.setPower(-SHOOTER_CLIP_POWER);
        } else {
            robot.shooterClipRight.setPower(0.0);
            robot.shooterClipLeft.setPower(0.0);
        }





        /*********************************************************
         *      COLLECTORS      Lt / RT       G2
         *      toggle rollers open/close
         */
        if (gamepad1.dpad_up ) {
            robot.collecter.setPower(COLLECTOR_POWER);
        } else if (gamepad1.dpad_down) {
            robot.collecter.setPower(-COLLECTOR_POWER);
        } else {
            robot.collecter.setPower(0.0);
        }



        // ----------------------shooter---------------------

        if (gamepad1.a) {
            robot.shooterRight.setPower(SHOOTER_POWER);
            robot.shooterLeft.setPower(SHOOTER_POWER);

        } else if (gamepad1.b){
            robot.shooterRight.setPower(0.0);
            robot.shooterLeft.setPower(0.0);
        }


        // ----------------------director------------------

        if (gamepad1.y) // go up
            directParicleOffset += DIRECT_PARTICLE_SPEED;
        else if (gamepad1.x)  // go down
            directParicleOffset -= DIRECT_PARTICLE_SPEED;

        directParicleOffset = Range.clip(directParicleOffset, DIRECT_PARTICLE_MIN, DIRECT_PARTICLE_MAX);
        robot.directParticle.setPosition(DIRECT_PARTICLE_BASE + directParicleOffset);



        // Send telemetry message to signify robot running;
        telemetry.addData("left roller",  "pos = %.2f", robot.leftRoller.getPosition());
        telemetry.addData("right roller",  "pos = %.2f", robot.rightRoller.getPosition());
        telemetry.addData("dirctor", "%.2f", robot.directParticle.getPosition());

        Color.RGBToHSV(robot.colorSensor0.red(), robot.colorSensor0.green(), robot.colorSensor0.blue(), hsvValues0);
        Color.RGBToHSV(robot.colorSensor1.red(), robot.colorSensor1.green(), robot.colorSensor1.blue(), hsvValues1);

        // send the info back to driver station using telemetry function.
        telemetry.addData("Clear0", robot.colorSensor0.alpha());
        telemetry.addData("Red  0", robot.colorSensor0.red());
        telemetry.addData("Green0", robot.colorSensor0.green());
        telemetry.addData("Blue 0", robot.colorSensor0.blue());
        telemetry.addData("Hue0", hsvValues0[0]);

        telemetry.addData("Clear1", robot.colorSensor1.alpha());
        telemetry.addData("Red  1", robot.colorSensor1.red());
        telemetry.addData("Green1", robot.colorSensor1.green());
        telemetry.addData("Blue 1", robot.colorSensor1.blue());
        telemetry.addData("Hue1", hsvValues1[0]);

        telemetry.addData("Wheels",  "at %7d :%7d",
                robot.leftDrive.getCurrentPosition(),
                robot.rightDrive.getCurrentPosition());

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

    public double deadZone(double input) {
        if (input < 0.08 && input > -0.08) {
            return 0.0;
        } else {
            return input;
        }
    }

}
