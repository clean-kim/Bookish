const path = require('path')

module.exports = {
    chainWebpack: config => {
        config.resolve.alias
            .set('@', path.resolve(__dirname, 'src/'))
    },
    outputDir: path.resolve(__dirname, "../" + "main/resources/static"),
    devServer: {
        proxy: {
            '/home': {
                target: 'http://localhost:8080',
                ws: true,
                changeOrigin: true
            }
        }
    },
    transpileDependencies: [
      'vuetify'
    ]
}
