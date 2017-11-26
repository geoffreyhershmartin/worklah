# worklah

## How does worklah actually work?

First, the socket server (`ServerSocket`) runs. When worklah connects to the server, the server opens a TCP connection between the server and the client.
We built worklah in a way that allows the server to open multiple concurrent connections when there are multiple clients requesting connections (as reflected in `_____`) )

 When this socket connection is established, we triggered certain methods like (_______) that will impact both the server side and client side). 
We also implemented methods to read/write from client to server, and vice versa. There was originally some confuction 
