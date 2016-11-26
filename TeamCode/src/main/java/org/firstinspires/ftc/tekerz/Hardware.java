package org.firstinspires.ftc.tekerz;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode, just hardware and const definitions
 *
 */
public class Hardware
{
    /***********************************************8
     *     ALL HARDWARE CLASSES
     */

    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor  collector = null;
    public DcMotor  shooterLoad  = null;
    public DcMotor  shooterFire = null;
    public Servo    leftRoller = null;
    public Servo    rightRoller = null;
    public Servo    directParticle = null;


    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public Hardware(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        //NAMES OF THINGS!!
        leftDrive = hwMap.dcMotor.get("left_drive");
        rightDrive = hwMap.dcMotor.get("right_drive");
        collector = hwMap.dcMotor.get("collector");
        shooterLoad = hwMap.dcMotor.get("shooter_load");
        shooterFire = hwMap.dcMotor.get("shooter_fire");

        leftRoller = hwMap.servo.get("left_roller");
        rightRoller = hwMap.servo.get("right_roller");
        directParticle = hwMap.servo.get("direct_particle");


        // Define and Initialize Motors
        leftDrive.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightDrive.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        collector.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        collector.setPower(0);
        shooterLoad.setPower(0);
        shooterFire.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        collector.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooterFire.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooterLoad.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos.
        directParticle.setDirection(Servo.Direction.REVERSE);

    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     */
    public void waitForTick(long periodMs) {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}

