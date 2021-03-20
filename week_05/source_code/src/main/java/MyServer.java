import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.Map;

public class MyServer {

    Server server;

    public static class Student {
        int id;
        String name;
        String dateOfBirth;
        String email;
        String address;

        public Student(int id, String name, String dateOfBirth, String email, String address) {
            this.id = id;
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            this.email = email;
            this.address = address;
        }
    }

    private static final Map<Integer, Student> data = Map.ofEntries(
            Map.entry(18021111, new Student(
                    18021111,
                    "Nguyen Duc Tai",
                    "07/07/2000",
                    "18021111@vnu.edu.vn",
                    "Duc Son, Anh Son, Nghe An"
            )),
            Map.entry(17154512, new Student(
                    17154512,
                    "Nguyen Duc An",
                    "25/10/2005",
                    "17154512@vnu.edu.vn",
                    "Long Son, Anh Son, Nghe An"))
    );

    private void start() throws IOException {
        int port = 50052;
        server = ServerBuilder.forPort(port)
                .addService(new GetStudentByIdImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        final MyServer server = new MyServer();
        server.start();
        server.blockUntilShutdown();
    }

    static class GetStudentByIdImpl extends GetStudentByIdGrpc.GetStudentByIdImplBase{
        @Override
        public void getStudentById(GetStudentByIdService.StudentId request, StreamObserver<GetStudentByIdService.StudentInfo> responseObserver) {
            GetStudentByIdService.StudentInfo.Builder info = GetStudentByIdService.StudentInfo.newBuilder();
            var student = data.get(request.getId());
            if (student != null) {
                info.setId(student.id);
                info.setDateOfBirth(student.dateOfBirth);
                info.setName(student.name);
                info.setAddress(student.address);
                info.setEmail(student.email);
            }
            responseObserver.onNext(info.build());
            responseObserver.onCompleted();
        }
    }
}
