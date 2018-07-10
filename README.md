# BGG Gallery Generator
I've just have a few megabytes webspace without PHP (or something else) support.
So I created this project, which generates a gallery of (and upload) my [BGG Games](https://boardgamegeek.com/user/JensG).

## Configuration
You need to provide some configurations. [See Spring Boot Doc](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html) to find out how you can do that.

The import configurations are:
* `bgg.username` - Your username on [Bgg](https://boardgamegeek.com/)
* `ftp.server` - FTP Server (IP/Hostname)
* `ftp.user` - FTP Username
* `ftp.password` - FTP Password