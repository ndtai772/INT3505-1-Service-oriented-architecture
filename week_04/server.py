import json

from flask import Flask

app = Flask(__name__)

with open("data.json") as f:
    data = json.loads(f.read())


@app.route('/')
def index():
    return {
        "api-endpoint": {
            "/full": "get full data",
            "/name": "get full name",
            "/family": "get family members"
        }
    }


@app.route('/full')
def get_full():
    return json.dumps(data)


@app.route('/name')
def get_name():
  names = []
  for p in data
    names.append(p["personalInfo"]["surname"] + " " + p["personalInfo"]["givenName"])
  return json.dumps(names)


@app.route('/family')
def get_family_members():
  families = []
  for p in data:
    name = p["personalInfo"]["surname"] + " " + p["personalInfo"]["givenName"];
    families.append({
      "fullName":name, 
      "family": p["personalInfo"]["family"]["person"]
      })
  return json.dumps(families)


app.run(host='0.0.0.0', port=8080, debug=True)
