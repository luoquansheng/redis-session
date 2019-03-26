# redis-session

这是两个应用之间共享session的demo

使用nginx做负载均衡

要启动这个项目，首相要把这个项目打包成一个jar文件，再打开cmd窗口去到jar文件
所在的文件夹运行此命令："java -jar jar包名称 --server.port 端口名称"（不包括双引号），用这个命令指定不同的端口号分别运行两次

注意：由于使用了redis做session共享、nginx做负载均衡，在项目启动前必须启动redis（端口号是6379）和nginx，以下是我的nginx配置

## nginx配置文件 start
http {
    include       mime.types;
    default_type  application/octet-stream;
    sendfile        on;
    keepalive_timeout  65;
    
    upstream redisSessionApp{
      server localhost:9001;
      server localhost:9002;
    }

    server {
        listen       9000;
        server_name  localhost;
        location / {
			    proxy_pass http://redisSessionApp;
        }
        error_page   500 502 503 504  /50x.html;
    }
}
## nginx配置文件 end

除了添加
upstream redisSessionApp{
  server localhost:9001;
  server localhost:9002;
}
和修改了这个
listen       9000;
location / {
  proxy_pass http://redisSessionApp;
}
之外，其它都是默认的

redis启动好和ngins配置好并启动之后启动应用，在浏览器输入 http://localhost:9001/set-session?sessionVal=abc 按回车之后
在端口号为9001的应用设置了一个session，按回车成功设置session，再就是在浏览器输入 http://localhost:9002/get-session 按回车之后
显示了在端口号为9001设置的session。

再输入nginx的配置访问两个应用 http://localhost:9000/get-session 成功显示session值（nginx默认的负载均衡方式是轮训，所以刷新一次浏览器会访问另外一个端口的应用）


