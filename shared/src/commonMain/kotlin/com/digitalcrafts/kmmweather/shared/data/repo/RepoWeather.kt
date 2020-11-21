package com.digitalcrafts.kmmweather.shared.data.repo

import com.digitalcrafts.kmmweather.shared.data.definitions.DataSourceWeather
import com.digitalcrafts.kmmweather.shared.data.impl.DataSourceImplWeather

class RepoWeather : DataSourceWeather by DataSourceImplWeather()