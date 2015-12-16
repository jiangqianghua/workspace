package ipc 

import "testing"

type EchoServer struct {

}

func (server *EchoServer)Handle(method , params string) *Response {
	return &Response{"ok","echo: "+method+ " ~ "+ params}
}

func (server *EchoServer) Name() string {
	return "EchoServer"
}

func TestIpc(t *testing.T){
	server := NewIpcSerer(&EchoServer{})

	client1 := NewIpcSerer(server)
	client2 := NewIpcSerer(server)

	resp1 , _ := client1.call("foo" , "From Client1")
	resp2 , _ := client2.call("foo" , "From Client")

	if resp1.Body != "echo: foo ~ From Client1" || resp2.Body != "echo: foo ~ From Client2" {
		t.Error("IpcClient.call failed . resp1 :" , resp1 , " resp2 : " , resp2)
	} 

	client1.close()
	client2.close()
}