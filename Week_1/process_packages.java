import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Request {
    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
    }

    public int arrival_time;
    public int process_time;
}

class Response {
    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }

    public boolean dropped;
    public int start_time;
}

class Buffer {
    public Buffer(int size) {
        this.size_ = size;
        this.finish_time_ = new ArrayList<Integer>();
        this.last_output_ = -1;
    }

    public Response Process(Request request) {
        if(this.size_ == 0){
            return new Response(true, -1);
        }

        // Remove completed process from buffer
        List<Integer> indexToRemove = new ArrayList<Integer>();
        for(int i = 0; i < this.finish_time_.size(); i++){
            if(this.finish_time_.get(i) <= request.arrival_time){
                indexToRemove.add(i);
            }else{
                break;
            }
        }
        for(int k : indexToRemove){
            this.finish_time_.remove(k);
        }

        // Add new request to buffer
        if(this.finish_time_.size() < this.size_){
            int processTime = (this.last_output_ == -1 || this.last_output_ < request.arrival_time) ? request.arrival_time : this.last_output_;
            if(request.process_time > 0){
                if(this.last_output_ > 0 && processTime <= this.last_output_){
                    this.last_output_ = this.last_output_ + request.process_time;
                }else{
                    this.last_output_ = request.process_time;
                }
            }
            this.finish_time_.add(processTime + request.process_time);
            return new Response(false, processTime);
        }else{
            return new Response(true, -1);
        }
    }

    private int size_;
    private ArrayList<Integer> finish_time_;
    private int last_output_;
}

class process_packages {
    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<Request>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }
        return requests;
    }

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<Response>();
        for (int i = 0; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int buffer_max_size = scanner.nextInt();
        Buffer buffer = new Buffer(buffer_max_size);

        ArrayList<Request> requests = ReadQueries(scanner);
        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        PrintResponses(responses);
    }
}
