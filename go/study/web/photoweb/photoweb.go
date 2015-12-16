package main 
// 例子主题是  web 相册功能， 有添加相片，删除相片，相片列表显示等功能，
import "io"
import "os"
import "log"
import "net/http"
import "html/template"
import "io/ioutil"
import "path"
import "runtime/debug"
//import "fmt"

// http://192.168.1.101:8082/upload
const	UPLOAD_DIR = "./uploads"
const 	TEMPLATE_DIR = "./views"
const 	ListDir = 0x0001

var templates map[string] *template.Template 


// 初始化加载所有的模板
//template.Must  表示确保模板不能解析成功，一定会除法错误流程
func init(){
	templates = make(map[string] *template.Template)  // 缓存
	fileInfoArr , err := ioutil.ReadDir(TEMPLATE_DIR) // 读取 views下的所有目录信息
	check(err)
	var templateName , templatePath string 
	for _, fileInfo := range fileInfoArr {
		templateName = fileInfo.Name()
		if ext := path.Ext(templateName) ; ext != ".html"{
			continue 
		}
		templatePath = TEMPLATE_DIR + "/" + templateName
		log.Println("Loading template:" , templatePath)
		t := template.Must(template.ParseFiles(templatePath))
		templates[templateName] = t //存放upload 和list 对应的模板信息
	}

/**
	func _ , tmp1 := range []string{"upload","list"} {
		t := template.Must(template.ParseFiles(tmp1+".html"))
		templates[tmp1] = t //存放upload 和list 对应的模板信息
	}
	*/
}

/**
func uploadHandler(w http.ResponseWriter , r *http.Request){
	if r.Method == "GET" {
		io.WriteString(w , "<form method=\"POST\" action=\"/upload\" " + 
			" enctype=\"multipart/form-data\" >"+
			"Choose an image to upload :<input name=\"image\" type=\"file\" />"+
			"<input type=\"submit\" value=\"Upload\" />"+
			"</form>")
		return 
	}
} */

// 通用的模板渲染器
func renderHtml(w http.ResponseWriter , tmp1 string , locals map[string]interface{}) (err1 error){
	err := templates[tmp1].Execute(w, locals)
	return err 
}

func uploadHandler(w http.ResponseWriter , r *http.Request){
	if r.Method == "GET" {
		/**
		t , err := template.ParseFiles("upload.html")
		if err != nil {
			http.Error(w , err.Error() , http.StatusInternalServerError)
			return 
		}
		t.Execute(w , nil)
		return 
		*/

		// 使用模板方式重新改方法
		err := renderHtml(w, "upload.html" , nil) 
		check(err)
		/**
		if err != nil {
			http.Error(w , err.Error() , http.StatusInternalServerError)
			return 
		}
		**/
	}

	if r.Method == "POST" {
		f , h, err := r.FormFile("image")
		check(err)
		/**
		if err != nil {
			http.Error(w , err.Error() ,http.StatusInternalServerError)
			return 
		}
		*/
		filename := h.Filename 
		defer f.Close()

		t , err := os.Create(UPLOAD_DIR + "/" + filename)
		check(err)
		/**
		if err != nil {
			http.Error(w , err.Error() ,http.StatusInternalServerError)
			return 
		}
		*/
		defer t.Close()

		
		 _, err = io.Copy(t , f) 
		 check(err)
		 /**
		 if err != nil {
			http.Error(w , err.Error() ,http.StatusInternalServerError)
			return 
		}
		*/

		http.Redirect(w , r , "/view?id=" + filename , http.StatusFound)
	}
}

func viewHandler(w http.ResponseWriter , r *http.Request){
	imageId := r.FormValue("id")
	imagePath := UPLOAD_DIR + "/"+imageId 
	if exists := isExists(imagePath) ; !exists {
		http.NotFound(w,r)
		return 
	}

	w.Header().Set("Content-Type" , "image")
	http.ServeFile(w, r , imagePath)
}

func isExists(path string) bool {
	_ , err := os.Stat(path)
	if err == nil {
		return true 
	}
	return os.IsExist(err)
}
func listHandler(w http.ResponseWriter , r *http.Request){
	fileInfoArr , err := ioutil.ReadDir(UPLOAD_DIR)
	if err != nil {
		http.Error(w , err.Error(),http.StatusInternalServerError)
		return 
	}
	locals := make(map[string]interface{})
	images := []string{}
	for _, fileInfo := range fileInfoArr {
		images = append(images , fileInfo.Name())
	}
	locals["images"] = images 
	/**
	t , err := template.ParseFiles("list.html")
	if err != nil {
		http.Error(w , err.Error(),http.StatusInternalServerError)
		return 
	}
	t.Execute(w , locals)
	*/
	// 利用渲染器编写
	if err = renderHtml(w, "list.html" , locals) ; err != nil {
		http.Error(w , err.Error(),http.StatusInternalServerError)
		return 
	}
	
}
//请求静态资源
func staticDirHandler(mux *http.ServeMux , prefix string , staticDir string , flags int){
	log.Println("staticDirHandler:" , prefix)
	mux.HandleFunc(prefix,func(w http.ResponseWriter , r *http.Request){
		file := staticDir + r.URL.Path[len(prefix)-1:]
	//	fmt.Println("file",file)
		log.Println("file:" , file)
		if (flags & ListDir) == 0 {
			if exists := isExists(file) ; !exists {
				http.NotFound(w, r)
			}
		}
		http.ServeFile(w,r,file)
		})
}
func safeHandler(fn http.HandlerFunc) http.HandlerFunc {
	return func(w http.ResponseWriter , r *http.Request) {
		defer func(){
			if e , ok := recover().(error); ok {
				http.Error(w , e.Error() , http.StatusInternalServerError)
				// 或者输出定义的错误页面
				log.Println("WARN: panic in %v - %v" , fn , e)
				log.Println(string(debug.Stack()))
			}
		}()
		fn(w , r)
	}
}

// 统一错误处理
func check(err error){
	if err != nil {
		panic(err)
	}
}
func main() {
	mux := http.NewServeMux()
	staticDirHandler(mux , "/assets/","./public" , 0)
	http.HandleFunc("/" , safeHandler(listHandler))
	http.HandleFunc("/view" , safeHandler(viewHandler))
	http.HandleFunc("/upload" , safeHandler(uploadHandler))
	err := http.ListenAndServe(":8082" , nil)
	if err != nil {
		log.Fatal("ListenAndServe :" , err )
	}
}