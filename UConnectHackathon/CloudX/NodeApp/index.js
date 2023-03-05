const express = require('express');
const app = express();
const port = 8000;

app.use(express.urlencoded());

app.use(express.static('./assets'));

app.set('view engine', 'ejs');
app.set('views', './views');


app.use('/', require('./routes'));

app.listen(port, function(err) {
    if(err) {
        console.log('err', err)
        return;
    }
    console.log(`Express is running at port: ${port}`);
});

