from flask import Flask, render_template, request, Response
from models.recommendation import get_recommendations

app = Flask(__name__)


@app.get('/')
def whatever():
    products = ['netbackup', 'backupexec', 'infoscale', 'merge1',
                'ediscovery', 'vault', 'alta', 'datainsight', 'netbackup_applicance']
    return render_template('index.html', products=products)


@app.route('/api/recommendations')
def recommendations():
    cart = request.args.get('cart', '')
    values = cart.split(',')
    recomends = list(get_recommendations(frozenset(values)))
    # print(recomends)
    return recomends


if __name__ == '__main__':
    app.run(debug=True)
