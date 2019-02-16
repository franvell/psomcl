/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testopencv;
import org.opencv.core.Mat;
/**
 *
 * @author Estefanis
 */
public class FbFd {
    private Mat fBrick;
    private Mat fDark;

    public FbFd(Mat fBrick, Mat fDark) {
        this.fBrick = fBrick;
        this.fDark = fDark;
    }

    public Mat getfBrick() {
        return fBrick;
    }

    public void setfBrick(Mat fBrick) {
        this.fBrick = fBrick;
    }

    public Mat getfDark() {
        return fDark;
    }

    public void setfDark(Mat fDark) {
        this.fDark = fDark;
    }
    
    
}
