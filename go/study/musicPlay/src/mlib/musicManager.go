package mlib

import "errors"

type MusicEntry struct {
	Id string 
	Name string 
	Artist string 
	Source string
	Type string 
}

type MusicManager struct {
	musics []MusicEntry
}

func NewMusicManager() *MusicManager {
	return &MusicManager{make([]MusicEntry,0)}
}

// 获取音乐列表的长度
func (m *MusicManager) Len() int {
	return len(m.musics)
}

// 根据index，获取音乐对象
func (m *MusicManager) Get(index int) (music *MusicEntry , err error) {
	if index < 0 || index >= len(m.musics) {
		return nil , errors.New("Index out of range")
	}
	return &m.musics[index],nil
}

// 根据名字查找音乐对象
func (m *MusicManager) Find(name string) *MusicEntry {
	if len(m.musics) == 0 {
		return nil
	}

	for _, m:= range m.musics{
		if m.Name == name {
			return &m
		}
	}
	return nil
}

// 添加音乐对象
func (m *MusicManager) Add(music *MusicEntry) {
	m.musics = append(m.musics , *music)
}

// 移除音乐
func (m *MusicManager) Remove(index int) *MusicEntry {
	if index < 0 || index >= len(m.musics) {
		return nil 
	}

	removedMusic := &m.musics[index]
	m.musics = append(m.musics[:index] , m.musics[index+1:]...)

	return removedMusic 
}
