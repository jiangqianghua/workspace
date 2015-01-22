import ctypes
ll = ctypes.cdll.LoadLibrary
lib = ll("./test.so")
lib.cfoo(1,4)
