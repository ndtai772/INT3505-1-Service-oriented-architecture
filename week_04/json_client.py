import requests
import json

def get_data_from(sub_url = ""):
    url = "https://json.ndtai772.repl.co" + sub_url
    print(">>Sent get request to " + url)
    response = requests.get(url)
    return response.content


print("Full data (raw): ")
full_data = get_data_from("/full")
full_data = json.loads(full_data)
print(full_data)

print("\nGet all member of team: ")
members = get_data_from("/name")
members = json.loads(members)
print(f"There are {len(members)} members:")
for mem in members:
    print(mem)

print("\nGet members' families: ")
families = get_data_from("/family")
families = json.loads(families)
for p in families:
    print("Gia dinh cua " + p["fullName"] + ": ")
    for m in p["family"]:
        print(f"Tên: {m['fullName']}, Sđt: {m['phoneNumber']}")
    print()