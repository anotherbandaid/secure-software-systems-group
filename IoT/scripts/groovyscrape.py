import csv
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
import time

browser = webdriver.Chrome(executable_path='/Users/wamuo/Downloads/chromedriver')

timeout = 10
all_github_links = {}
last_title = ""

csv_file = open('github_link_scrape.csv', 'w')

csv_writer = csv.writer(csv_file)
csv_writer.writerow(['title', 'page_link', 'github_link'])


def open_home(page):
    browser.get("https://community.smartthings.com/c/projects-stories")

    if page > 0:
        html_element = browser.find_element_by_tag_name('html')
        while page > 0:
            time.sleep(5)
            html_element.send_keys(Keys.END)
            time.sleep(5)
            page -= 1

    try:
        WebDriverWait(browser, timeout).until(
            EC.visibility_of_element_located((By.XPATH, "//a[@class='title raw-link raw-topic-link']")))
    except TimeoutException:
        print("Timed out waiting for page to load")
        browser.quit()

    title_elements = browser.find_elements_by_xpath("//a[@class='title raw-link raw-topic-link']")
    title_links = [link.get_attribute('href') for link in title_elements]
    titles = [title.text for title in title_elements]

    global last_title

    if last_title:
        last_index = titles.index(last_title)
        title_links = title_links[last_index + 1:]
        titles = titles[last_index + 1:]

    last_title = titles[-1]
    print("Last item is: %s" % last_title)

    get_links_from_page(titles, title_links)


def get_links_from_page(titles, links):
    for i in range(len(links)):
        href = links[i]
        title = titles[i]

        browser.get(href)
        try:
            WebDriverWait(browser, timeout).until(
                EC.visibility_of_element_located((By.XPATH, "//a[@class='fancy-title']")
                                                 )
            )
        except TimeoutException:
            print("Timed out waiting for page to load")
            browser.quit()

        github_link_elements = browser.find_elements_by_xpath('//a[contains(@href,"github")]')
        github_links = [git_link.get_attribute('href') for git_link in github_link_elements]

        all_github_links[title] = github_links
        csv_writer.writerow([title, href, github_links])
        print(all_github_links[title])


# There are 127 pages
for page in range(126):
    print(page)
    open_home(page)
    print(len(all_github_links))

csv_file.close()
