#       CƠ BẢN VỀ ELASTICSEARCH 
### 1. Query 
##### 1.1 Match clause: Return all message match with one of in the string
Example: 
```aidl
POST http://localhost:9200/logback-2021.12.27/_search
Content-Type: application/json

{
  "query": {
    "bool": {
      "must": [{"match":
        {
          "message":"Total Trans: 7"
        }
        }
      ]
    }
  }
}
```
##### 1.2 Match Phrase clause: Return all message match with one of in the string IN ORDER. It's same with double quote when using Google search ""
https://bktranquangchung.wordpress.com/2018/09/14/mot-so-query-pho-bien-trong-elasticsearch/