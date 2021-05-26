import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;

  constructor(private accountService: AccountService, private loginModalService: LoginModalService) {
    const card = document.getElementsByClassName('jh-card')[0] as HTMLElement;
    card.classList.remove('jh-card');
    card.classList.add('jh-card-custom');

    const footer = document.getElementsByTagName('jhi-footer')[0] as HTMLElement;
    footer.style.visibility = 'hidden';
  }

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }

    const card = document.getElementsByClassName('jh-card-custom')[0] as HTMLElement;
    if (card !== undefined) {
      card.classList.add('jh-card');
      card.classList.remove('jh-card-custom');
    }

    const footer = document.getElementsByTagName('jhi-footer')[0] as HTMLElement;
    if (footer !== undefined) {
      footer.style.visibility = 'visible';
    }
  }
}
