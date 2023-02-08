@RestController
class HelloController {
    @GetMapping("/")
    def hello() {
        return "hell"
    }

}
