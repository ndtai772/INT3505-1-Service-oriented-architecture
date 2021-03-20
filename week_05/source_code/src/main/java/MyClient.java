import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MyClient {
    private final GetStudentByIdGrpc.GetStudentByIdBlockingStub blockingStub;

    public MyClient(Channel channel) {
        this.blockingStub = GetStudentByIdGrpc.newBlockingStub(channel);
    }


    public void getStudentInfoById(int id) {
        System.out.println("Will try to get info of studentId: " + id);
        GetStudentByIdService.StudentId studentId = GetStudentByIdService.StudentId.newBuilder().setId(id).build();
        GetStudentByIdService.StudentInfo response;
        response = blockingStub.getStudentById(studentId);
        if (response.getId() != id) {
            System.out.println("StudentId wasn't exited");
        } else System.out.printf(
                "Id: %d \nName: %s \nEmail: %s \nDateOfBirth: %s \nAddress: %s\n",
                response.getId(),
                response.getName(),
                response.getEmail(),
                response.getDateOfBirth(),
                response.getAddress()
        );
    }

    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:50052";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Student Id: ");
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        try {
            MyClient client = new MyClient(channel);
            client.getStudentInfoById(scanner.nextInt());
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
