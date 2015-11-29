package simplemath

import "testing"

func TestAdd1(t *testing.T){
	r := Add(1,2)
	if r != 3 {
		t.Errorf("Add(1,2) failed. GO %d ,expected 3.",r)
	}
}

