package test;

import java.nio.ByteBuffer;

/**
 * Created by root on 6/29/17.
 */
public class Test {

    private static String strBuf = "TrackingPetugas\n" +
            "GrafikRealisaiSambungan\n" +
            "AssignWoSambung\n" +
            "PolaBayaTrackingPetugas\n" +
            "GrafikRealisaiSambungan\n" +
            "AssignWoSambung\n" +
            "PolaBayaTrackingPetugas\n" +
            "GrafikRealisaiSambungan\n" +
            "AssignWoSambung\n" +
            "PolaBayaTrackingPetugas\n" +
            "GrafikRealisaiSambungan\n" +
            "AssignWoSambung\n" +
            "PolaBayaTrackingPetugas\n" +
            "GrafikRealisaiSambungan\n" +
            "AssignWoSambung\n" +
            "PolaBayaTrackingPetugas\n" +
            "GrafikRealisaiSambungan\n" +
            "AssignWoSambung\n" +
            "PolaBayaTrackingPetugas\n" +
            "GrafikRealisaiSambungan\n" +
            "AssignWoSambung\n" +
            "PolaBayaTrackingPetugas\n" +
            "GrafikRealisaiSambungan\n" +
            "AssignWoSambung\n" +
            "PolaBayaTrackingPetugas\n" +
            "GrafikRealisaiSambungan\n" +
            "AssignWoSambung\n" +
            "PolaBayaTrackingPetugas\n" +
            "GrafikRealisaiSambungan\n" +
            "AssignWoSambung\n" +
            "PolaBayaTrackingPetugas\n" +
            "GrafikRealisaiSambungan\n" +
            "AssignWoSambung\n" +
            "PolaBayaTrackingPetugas\n" +
            "GrafikRealisaiSambungan\n" +
            "AssignWoSambung\n" +
            "PolaBayaTrackingPetugas\n" +
            "GrafikRealisaiSambungan\n" +
            "AssignWoSambung\n" +
            "PolaBaya";

    public static final byte[] buffer = strBuf.getBytes(); // 832 bytes

    public static void main(String[] args) {
        byte[] b1 = new byte[10];
        byte[] b2 = new byte[20];
        ByteBuffer byteBuf = ByteBuffer.wrap(buffer);

        System.out.println("Capacity  : " + byteBuf.capacity());
        System.out.println("Position  : " + byteBuf.position());
        System.out.println("Limit     : " + byteBuf.limit());
        System.out.println("Remaining : " + byteBuf.remaining());

        System.out.println("Get 10 bytes from buffer");

        byteBuf.get(b1);
        System.out.println("Capacity  : " + byteBuf.capacity());
        System.out.println("Position  : " + byteBuf.position());
        System.out.println("Limit     : " + byteBuf.limit());
        System.out.println("Remaining : " + byteBuf.remaining());

//        System.out.println("Flip        : " + byteBuf.flip());
//        System.out.println("Rewind      : " + byteBuf.rewind());

        System.out.println(byteBuf = byteBuf.slice());

        System.out.println("Capacity  : " + byteBuf.capacity());
        System.out.println("Position  : " + byteBuf.position());
        System.out.println("Limit     : " + byteBuf.limit());
        System.out.println("Remaining : " + byteBuf.remaining());

    }
}
