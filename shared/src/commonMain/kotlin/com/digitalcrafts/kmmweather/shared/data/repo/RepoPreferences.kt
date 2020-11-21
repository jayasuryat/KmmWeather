package com.digitalcrafts.kmmweather.shared.data.repo

import com.digitalcrafts.kmmweather.shared.data.definitions.DataSourcePreferences
import com.digitalcrafts.kmmweather.shared.data.impl.DataSourceImplPreferences

class RepoPreferences : DataSourcePreferences by DataSourceImplPreferences()