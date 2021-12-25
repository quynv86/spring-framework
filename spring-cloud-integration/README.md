### Think About Spring Integration File Inbound Adapter
#####1. Dealing with Incomplete Data

```aidl
/*
Another common technique is to write a second “marker” file to indicate that the file transfer is complete
In this scenario, you should not consider somefile.txt (for example) to be available for use until somefile.txt.complete is also present
Spring Integration version 5.0 introduced new filters to support this mechanism. 
Implementations are provided for the file system (FileSystemMarkerFilePresentFileListFilter), FTP and SFTP. 
They are configurable such that the marker file can have any name, although it is usually related to the file being transferred. 
See the Javadoc for more information.

Link : https://docs.spring.io/spring-integration/reference/html/file.html

 Example:
*/ 
        return IntegrationFlows.from(
                fileInboundAdapter()
                , e -> e.poller(Pollers.fixedDelay(fixedDelay()))
        ).split(Files.splitter().markers())
         .filter(p -> !(p instanceof FileSplitter.FileMarker))
         .transform(Transformers.converter(this.movieConverter))
         .transform(Transformers.toJson())
         .handle(httpOutboundAdapter())
         .get();
         
```
