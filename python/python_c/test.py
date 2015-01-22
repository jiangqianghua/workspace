import ctypes
# python run c code
ll = ctypes.cdll.LoadLibrary
lib = ll("./test.so")
lib.foo(1,3)
