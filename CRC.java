public class CRCCCITT {
    public static void main(String[] args) {
        String message = "Hello, CRC!";
        int crc = calculateCRC(message);
        
        System.out.println("Original Message: " + message);
        System.out.println("CRC: " + Integer.toHexString(crc));
        
        // Simulate transmission by appending the CRC to the message
        String transmittedMessage = message + Integer.toHexString(crc);
        
        // Simulate receiving the transmitted message
        boolean isMessageValid = verifyCRC(transmittedMessage);
        
        if (isMessageValid) {
            System.out.println("Received Message is Valid");
        } else {
            System.out.println("Received Message is Invalid");
        }
    }
    
    public static int calculateCRC(String message) {
        int crc = 0xFFFF; // Initialize with 16 ones (0xFFFF)
        int polynomial = 0x1021; // CRC-CCITT polynomial (16-bit)

        byte[] bytes = message.getBytes();
        
        for (byte b : bytes) {
            crc ^= (b << 8); // XOR with the next byte
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0) {
                    crc = (crc << 1) ^ polynomial;
                } else {
                    crc <<= 1;
                }
            }
        }
        
        return crc & 0xFFFF;
    }
    
    public static boolean verifyCRC(String transmittedMessage) {
        int crc = calculateCRC(transmittedMessage.substring(0, transmittedMessage.length() - 4)); // Remove CRC from the end
        int receivedCRC = Integer.parseInt(transmittedMessage.substring(transmittedMessage.length() - 4), 16);
        
        return crc == receivedCRC;
    }
}
