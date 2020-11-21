import SwiftUI
import shared

func greet() -> String {
    
    let lat : Double = 21.1702
    let lon : Double = 72.8311
    
    let repoWeather: DataSourceWeather =  RepoWeather() 
    
    repoWeather.getWeatherData(lat: <#T##Double#>, long: <#T##Double#>, completionHandler: <#T##(RemoteResponse<WeatherData>?, Error?) -> Void#>)
    
    return PlatformUtils().getRandomUUID()
}

struct ContentView: View {
    var body: some View {
        Text(greet())
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
