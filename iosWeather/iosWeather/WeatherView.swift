import SwiftUI
import shared
import Combine
import KingfisherSwiftUI

struct WeatherView: View {
    
    @ObservedObject var viewModel: WeatherViewModel
    
    init() {
        self.viewModel = WeatherViewModel()
        viewModel.getWeatherInformation()
    }
    
    var body: some View {
        content()
    }
}

extension WeatherView {
    
    var loading: some View {
        VStack {
            Text("Loading weather...")
                   .foregroundColor(.white)
        }
        .background(Color.black.opacity(0.92))
    }
    
    func content() -> some View {
      if viewModel.weatherData != nil {
        return AnyView(details(for: viewModel))
      } else {
        return AnyView(loading)
      }
    }
    
    func details(for viewModel: WeatherViewModel) -> some View {
        
        VStack(alignment: .leading) {
            
            VStack(alignment: .leading) {
                
                Text(viewModel.getTime())
                    .font(.callout)
                    .fontWeight(.light)
                    .foregroundColor(.white)
                    .padding(.top, 20)
                
                Text(viewModel.getPlace())
                    .foregroundColor(.white)
                    .font(.title)
                    .multilineTextAlignment(.leading)
                
            }
            .padding()
            
            Spacer()
            
            HStack(alignment: .center, spacing: 10) {
                
                VStack(alignment: .leading) {
                    
                    Text(String(format: "%.2f°", viewModel.getTemparature()))
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                        .font(.system(size: 55)).bold()
                        .multilineTextAlignment(.leading)
                        .padding(.bottom, 10)
                    
                    Text(String(format: "Feels Like %.2f°C", viewModel.getFeelsLike()))
                        .foregroundColor(.gray)
                        .font(.system(size: 15))
                        .padding(.bottom, 10)
                    
                    Text(viewModel.getDescription())
                        .fontWeight(.regular)
                        .foregroundColor(.white)
                        .font(.system(size: 20))
                        .padding(.bottom, 10)
                }
                
                Spacer()
                
                KFImage(URL(string: "http://openweathermap.org/img/wn/50n@2x.png")!)
            }
            .padding()
            
            Spacer()
        }
        .padding()
        .background(Color.black.opacity(0.92))
        .edgesIgnoringSafeArea(.all)
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        WeatherView()
    }
}


public class WeatherViewModel: ObservableObject, Identifiable {
    
    @Published var weatherData: WeatherData?
    
    func getWeatherInformation() {
        
        let lat : Double = 12.978165599999999
        let lon : Double = 77.7688597
        
        let repoWeather: DataSourceWeather =  RepoWeather()
        
        repoWeather.getWeatherData(lat: lat, long: lon) { (data, error) in
            if data?.isSuccess ?? false {
                if let wData = (data! as! RemoteResponseSuccess).data {
                    self.weatherData = wData
                    print(wData.weather.first?.description())
                    print(NSDate(timeIntervalSince1970: TimeInterval(wData.dt)))
                }
            }
        }
//        return PlatformUtils().getRandomUUID()
    }
    
    func getTime() -> String {
        if let date = weatherData?.dt {
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "hh:mm a, dd MMM YYY"
            return dateFormatter.string(from: Date(timeIntervalSince1970: TimeInterval(date)))
        }
        return "N/A"
    }
    
    func getPlace() -> String {
        return weatherData?.name ?? "N/A"
    }
    
    func getTemparature() -> Double {
        return weatherData?.main.temp ?? 0.0
    }
    
    func getFeelsLike() -> Double {
        return weatherData?.main.feels_like ?? 0.0
    }
    
    func getDescription() -> String {
        return weatherData?.weather.first?.descr.capitalized ?? "N/A"
    }
}
