import csv
import urllib.parse
import ast

all_github_links = []

csv_file = open('gitlink_scrape.csv', 'r+')
output = open('repos.csv', 'w')

csv_reader = csv.reader(csv_file)
csv_writer = csv.writer(output)

for row in csv_reader:
    list_string = row[2]
    if not list_string == 'github_link':
        links = ast.literal_eval(list_string)
        all_github_links.extend(links)


def unique(list1):
    list_set = set(list1)
    return list(list_set)


all_github_links = unique(all_github_links)


def get_repo(url):
    url_parts = urllib.parse.urlparse(url)
    paths = url_parts[2].split('/')

    if len(paths) > 2:
        repo_link = url_parts[0] + '://' + url_parts.hostname + '/' + paths[1] + '/' + paths[2]
        csv_writer.writerow([repo_link])
        return repo_link
    else:
        print(url)


[get_repo(link) for link in all_github_links if 'github.com' in link]

csv_file.close()
output.close()
