use serde_json::Value;
use curl::easy::Easy;
use std::io::stdin;

fn rust_client(endpoint: &str, func: fn(Value) -> ()) {
    let mut client = Easy::new();
    let url = format!("https://json.ndtai772.repl.co{}", endpoint);
    println!("Sending get request to : {}", url);
    client.url(&url).expect("cannot get data from url");

    let mut transfer = client.transfer();
    transfer.write_function(|data| {
        func(serde_json::from_slice(data).expect("cannot parse data to json"));
        Ok(data.len())
    }).unwrap();
    transfer.perform().unwrap();
}

fn main() {
    fn get_input() -> String {
        let mut input = String::new();
        stdin().read_line(&mut input).expect("Cannot read input from stdin!");
        input
    }
    rust_client("/", |v| {
        println!("What this client could do: ");
        let endpoints = v["api-endpoint"].as_object().expect("#1");
        let jobs = endpoints.iter().enumerate().collect::<Vec<_>>();
        jobs.iter().for_each(|(i, (_k, v))| {
            println!("{}.{}", i, v.as_str().expect("#2"));
        });

        println!("What do you want? (q to quit) ");
        loop {
            let s = get_input();
            if s.trim().eq("q") {
                println!("quit!");
                return;
            }
            match s.trim().parse::<u8>() {
                Ok(0) => {
                    let (_, (k, _v)) = jobs[0];
                    rust_client(k, |data| {
                        let data = data.as_array().expect("Not an array!");
                        data.iter().filter_map(|p| p.as_object()).for_each(|people| {
                            println!("{}", people["fullName"]);
                            let family = people["family"].as_array().expect("#6");
                            println!("Gia dinh co {} nguoi: ", family.len());
                            family.iter().for_each(|p| {
                                let p = p.as_object().expect("#4");
                                println!("Tên: {}, Địa chỉ: {}, Sđt: {}", p["fullName"], p["address"], p["phoneNumber"]);
                            })
                        });
                    })
                }
                Ok(i) => {
                    if let Some((_, (k, _v))) = jobs.get(i as usize) {
                        rust_client(k, |v| { println!("{}", v); });
                    }
                }
                _ => {}
            }
        }
    });
}
