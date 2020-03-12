import json
import requests
from bs4 import BeautifulSoup
import csv
from pathlib import Path

all_github_links = {}

path = Path('gitlink_scrape.csv')
file_exists = path.exists()

if file_exists:
    csv_file = open('gitlink_scrape.csv', 'r+')
else:
    csv_file = open('gitlink_scrape.csv', 'w')

csv_writer = csv.writer(csv_file)

if file_exists:
    csv_reader = csv.reader(csv_file)
    for row in csv_reader:
        all_github_links[row[0]] = row[2]

else:
    csv_writer.writerow(['title', 'page_link', 'github_link'])


def load_json(page):
    response = requests.get(f"https://community.smartthings.com/c/projects-stories/10.json?page={page}")
    page_content = json.loads(response.text)

    topics = page_content['topic_list']['topics']

    title_links = []
    for topic in topics:
        title = topic['title']
        topic_link = "https://community.smartthings.com/t/" + topic['slug'] + "/" + str(topic['id'])
        title_links.append((title, topic_link))

    get_github_links(title_links)
    print(f"finished page: {page}")


def unique(list1):
    list_set = set(list1)
    return list(list_set)


def get_github_links(title_links):
    for title_link in title_links:
        source = requests.get(title_link[1]).text
        soup = BeautifulSoup(source, 'lxml')
        github_links = [link['href'] for link in soup.select("a[href*=github]")]

        if title_link[0] not in all_github_links and not len(github_links) == 0:
            unique_links = unique(github_links)
            all_github_links[title_link[0]] = unique_links
            csv_writer.writerow([title_link[0], title_link[1], unique_links])


for page in range(5):
    load_json(page)

csv_file.close()
